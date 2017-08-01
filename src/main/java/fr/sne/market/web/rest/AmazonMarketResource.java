package fr.sne.market.web.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amazon.api.ECS.client.jax.Cart;
import com.amazon.api.ECS.client.jax.CartAddResponse;
import com.amazon.api.ECS.client.jax.CartClearResponse;
import com.amazon.api.ECS.client.jax.CartCreateResponse;
import com.amazon.api.ECS.client.jax.CartGetResponse;
import com.amazon.api.ECS.client.jax.CartItem;
import com.amazon.api.ECS.client.jax.CartItems;
import com.amazon.api.ECS.client.jax.CartModifyResponse;
import com.amazon.api.ECS.client.jax.Errors;
import com.amazon.api.ECS.client.jax.Item;
import com.amazon.api.ECS.client.jax.ItemLookupResponse;
import com.amazon.api.ECS.client.jax.ItemSearchResponse;
import com.amazon.api.ECS.client.jax.Items;
import com.amazon.api.ECS.client.jax.OperationRequest;
import com.codahale.metrics.annotation.Timed;

import fr.sne.market.service.AmazonMarketService;
import fr.sne.market.service.dto.CartDTO;
import fr.sne.market.service.dto.CartItemDTO;
import fr.sne.market.service.dto.MarketItemDTO;
import fr.sne.market.service.mapper.CartItemMapper;
import fr.sne.market.service.mapper.CartMapper;
import fr.sne.market.service.mapper.MarketItemDetailsMapper;
import fr.sne.market.service.mapper.MarketItemMapper;

/**
 * REST controller for managing Amazon items.
 */
@RestController
@RequestMapping("/api")
public class AmazonMarketResource {

	private final Logger log = LoggerFactory.getLogger(AmazonMarketResource.class);

	private final AmazonMarketService amazonMarketService;
	private final RestTemplate restTemplate; // todo - move in service
	private final MarketItemMapper marketItemMapper;
	private final MarketItemDetailsMapper marketItemDetailsMapper;
	private final CartMapper cartMapper;
	private final CartItemMapper cartItemMapper;

	@Autowired
	public AmazonMarketResource(AmazonMarketService amazonMarketService, RestTemplateBuilder restTemplateBuilder,
			MarketItemMapper marketItemMapper, MarketItemDetailsMapper marketItemDetailsMapper, CartMapper cartMapper,
			CartItemMapper cartItemMapper) {
		this.amazonMarketService = amazonMarketService;
		this.restTemplate = restTemplateBuilder.build();
		this.marketItemMapper = marketItemMapper;
		this.marketItemDetailsMapper = marketItemDetailsMapper;
		this.cartMapper = cartMapper;
		this.cartItemMapper = cartItemMapper;
	}

	/**
	 * SEARCH /search/products?query=:query : search for the product
	 * corresponding to the query. ItemSearch returns up to 10 search results
	 * per page
	 */
	@GetMapping("/amazon/search")
	@Timed
	public List<MarketItemDTO> searchAmazonItem(@RequestParam String query,
			@RequestParam(required = false, defaultValue = "All") String searchindex,
			@RequestParam(required = false, defaultValue = "1") String itempage) throws Exception {

		log.debug("REST request to search for a page of Customers for query {}", query);
		log.info("GET - showItemSearch ==> keywords = " + query + " | searchindex = " + searchindex + " | itemPage = "
				+ itempage);

		String targetUrl = amazonMarketService.itemSearch(query, searchindex, itempage);
		URI targetURI = new URI(targetUrl);

		ResponseEntity<ItemSearchResponse> result = restTemplate.exchange(targetURI, HttpMethod.GET, null,
				ItemSearchResponse.class);

		OperationRequest operationRequest = result.getBody().getOperationRequest();

		Items resultItems = result.getBody().getItems().get(0);

		HttpStatus statusCode = result.getStatusCode();

		log.info("statusCode : " + statusCode);
		if (statusCode == HttpStatus.OK) {
			Errors errors = resultItems.getRequest().getErrors();

			if (errors != null && errors.getError().size() > 0) {
				log.info(errors.getError().get(0).toString());
			} else {
				List<Item> listItems = resultItems.getItem();
				log.info("allItemsResults = " + listItems.get(0).getItemAttributes().toString());
			}
		} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE) {
			log.info("serviceUnavailable, Amazon Service is Currently Unavailable ");
		}

		// MarketItemAttributesDTO marketItemAttributes = new
		// MarketItemAttributesDTO();
		// marketItemAttributes.setManufacturer(manufacturer);

		// MarketItemDTO marketItem = new MarketItemDTO();

		return marketItemMapper.toDto(resultItems.getItem());
	}

	/**
	 * ITEM LOOKUP - The ItemLookup operation returns item attributes.
	 * 
	 */
	@GetMapping("/amazon/lookup/{asin}")
	@Timed
	public ResponseEntity<MarketItemDTO> getIAmazonItem(@PathVariable String asin) throws Exception {

		log.debug("REST request to get Item Details - ItemLookup asin : {}", asin);

		String targetUrl = amazonMarketService.itemLookup(asin);
		URI targetURI = new URI(targetUrl);

		ResponseEntity<ItemLookupResponse> result = restTemplate.exchange(targetURI, HttpMethod.GET, null,
				ItemLookupResponse.class);

		// OperationRequest operationRequest =
		// result.getBody().getOperationRequest();
		Items resultItems = result.getBody().getItems().get(0);

		// HttpStatus statusCode = result.getStatusCode();

		// log.info("statusCode : " + statusCode);

		// Errors errors = resultItems.getRequest().getErrors();

		// get one item lookup detail
		Item lookeditem = resultItems.getItem().get(0);

		// return lookeditem;

		HttpHeaders header = null;
		return Optional.ofNullable(marketItemDetailsMapper.marketItemToMarketItemDTO(lookeditem))
				.map(response -> ResponseEntity.ok().headers(header).body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

		// return Optional.ofNullable(resultItems.getItem().get(0))
		// .map(item -> new ResponseEntity<>(new MarketItemDTO(Item),
		// HttpStatus.OK))
		// .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

	}

	/***
	 * Get/Clear remote remote shopping cart.
	 * 
	 * The CartGet operation returns the IDs, quantities, and prices of all
	 * items in a remote shopping cart, including SavedForLater items
	 */
	@GetMapping("/amazon/cart/{operation}/{cartId}")
	public ResponseEntity<CartDTO> manageAmazonCart(@PathVariable String cartId, @PathVariable String operation,
			@RequestParam(required = true) String hmac, @RequestParam(required = false) String asin,
			@RequestParam(required = false) String quantity) throws Exception {

		log.info("GET - /cart/" + operation + "/" + cartId);
		log.debug("REST request to Get remote remote shopping cart {}", cartId);
		log.info("GET - /amazon/cart/ ==> operation = " + operation + " | hmac = " + hmac);
		// log.info("asin : "+ asin + "| quantity : " + quantity + "| operation
		// : " + operation);

		String responseGroup = "Cart";
		HttpStatus statusCode;
		Cart cart;
		OperationRequest operationRequest;

		if (operation.equalsIgnoreCase("CartClear")) {
			String targetUrl = this.amazonMarketService.getCartSample(cartId, hmac, responseGroup, operation);
			URI targetURI;
			targetURI = new URI(targetUrl);

			ResponseEntity<CartClearResponse> result = restTemplate.exchange(targetURI, HttpMethod.GET, null,
					CartClearResponse.class);
			operationRequest = result.getBody().getOperationRequest();
			cart = result.getBody().getCart().get(0);
			statusCode = result.getStatusCode();

		} else if (operation.equalsIgnoreCase("CartAdd")) {
			String targetUrl = this.amazonMarketService.cartAddSample(cartId, hmac, asin, quantity, responseGroup,
					operation);
			URI targetURI;
			targetURI = new URI(targetUrl);

			ResponseEntity<CartAddResponse> result = restTemplate.exchange(targetURI, HttpMethod.GET, null,
					CartAddResponse.class);
			operationRequest = result.getBody().getOperationRequest();
			cart = result.getBody().getCart().get(0);
			statusCode = result.getStatusCode();
			log.info(statusCode.toString());

		} else if (operation.equalsIgnoreCase("CartModify")) {
			String targetUrl = this.amazonMarketService.cartAddSample(cartId, hmac, asin, quantity, responseGroup,
					operation);
			URI targetURI;
			targetURI = new URI(targetUrl);
			ResponseEntity<CartModifyResponse> result = restTemplate.exchange(targetURI, HttpMethod.GET, null,
					CartModifyResponse.class);
			operationRequest = result.getBody().getOperationRequest();
			cart = result.getBody().getCart().get(0);
			statusCode = result.getStatusCode();
			log.info(statusCode.toString());
		}
		// CartGet
		else {
			String targetUrl = this.amazonMarketService.getCartSample(cartId, hmac, responseGroup, operation);
			URI targetURI;
			targetURI = new URI(targetUrl);

			ResponseEntity<CartGetResponse> result = restTemplate.exchange(targetURI, HttpMethod.GET, null,
					CartGetResponse.class);
			operationRequest = result.getBody().getOperationRequest();
			statusCode = result.getStatusCode();
			cart = result.getBody().getCart().get(0);
		}

		log.info("statusCode : " + statusCode);

		if (statusCode == HttpStatus.OK) {
			Errors errors = cart.getRequest().getErrors();

			if (errors != null && errors.getError().size() > 0) {
				log.info(errors.getError().get(0).toString());
				log.info("errorCode : " + errors.getError().get(0).getCode());
				log.info("errorMsg : " + errors.getError().get(0).getMessage());
			} else {
				log.info("cart");
			}
		} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE) {
			log.info("serviceUnavailable", "Amazon Service is Currently Unavailable ");
		}

		// return Optional.ofNullable(
		// cart.map(item -> new ResponseEntity<>(item, HttpStatus.OK))
		// .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

		CartDTO cartDTO = this.cartMapper.cartToCartDTO(cart);
		Optional<Cart> optionalCart = Optional.ofNullable(cart);
		Optional<List<CartItem>> optionalCartItems = optionalCart.map(Cart::getCartItems).map(CartItems::getCartItem);
		// .ifPresent(System.out::println);
		if (optionalCartItems.isPresent()) {
			List<CartItemDTO> cartItemDTO = this.cartItemMapper.toDTOs(optionalCartItems.get());
			cartDTO.setCartItem(cartItemDTO);
		}

		HttpHeaders header = null;
		return Optional.ofNullable(cartDTO).map(response -> ResponseEntity.ok().headers(header).body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	/**
	 * Create a new cart
	 */
	@GetMapping("/amazon/cart/CartCreate")
	public ResponseEntity<CartDTO> createAmazonCart(@RequestParam(required = true) String operation,
			@RequestParam(required = false) String asin, @RequestParam(required = false) String quantity)
			throws Exception {

		log.info(" GET - /cart/CartCreate");
		log.info("[asin = " + asin + "] [quantity = " + quantity + "] [operation = " + operation + "]");

		log.info("checkCreateCartForm()");

		HttpStatus statusCode;
		Cart cart;
		OperationRequest operationRequest;

		URI targetURI;
		String targetUrl = this.amazonMarketService.createCartSample(asin, quantity, "Cart");

		targetURI = new URI(targetUrl);

		ResponseEntity<CartCreateResponse> result = restTemplate.exchange(targetURI, HttpMethod.GET, null,
				CartCreateResponse.class);

		operationRequest = result.getBody().getOperationRequest();
		statusCode = result.getStatusCode();
		log.info("statusCode : " + statusCode);
		// log.info("getCart().size() : " + result.getBody().getCart().size());

		cart = result.getBody().getCart().get(0);
		// log.info(cart.toString());

		if (statusCode == HttpStatus.OK) {
			Errors errors = cart.getRequest().getErrors();

			if (errors != null && errors.getError().size() > 0) {
				log.info(errors.getError().get(0).toString());
				log.info("errorCode : " + errors.getError().get(0).getCode());
				log.info("errorMsg : " + errors.getError().get(0).getMessage());
			} else {
				log.info("cart");
			}
		} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE) {
			log.info("serviceUnavailable", "Amazon Service is Currently Unavailable ");
		}

		CartDTO cartDTO = this.cartMapper.cartToCartDTO(cart);
		Optional<Cart> optionalCart = Optional.ofNullable(cart);
		Optional<List<CartItem>> optionalCartItems = optionalCart.map(Cart::getCartItems).map(CartItems::getCartItem);
		if (optionalCartItems.isPresent()) {
			List<CartItemDTO> cartItemDTO = this.cartItemMapper.toDTOs(optionalCartItems.get());
			cartDTO.setCartItem(cartItemDTO);
		}

		HttpHeaders header = null;
		return Optional.ofNullable(cartDTO).map(response -> ResponseEntity.ok().headers(header).body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
