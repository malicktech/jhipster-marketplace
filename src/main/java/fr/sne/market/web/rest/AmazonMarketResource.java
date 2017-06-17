package fr.sne.market.web.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amazon.advertising.api.enums.SearchIndex;
import com.amazon.advertising.api.helpers.SignedRequestsHelper;
import com.amazon.api.ECS.client.jax.Errors;
import com.amazon.api.ECS.client.jax.Item;
import com.amazon.api.ECS.client.jax.ItemLookupResponse;
import com.amazon.api.ECS.client.jax.ItemSearchResponse;
import com.amazon.api.ECS.client.jax.Items;
import com.amazon.api.ECS.client.jax.OperationRequest;
import com.codahale.metrics.annotation.Timed;

import fr.sne.market.service.AmazonMarketService;
import fr.sne.market.service.dto.MarketItemDTO;
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
	private final RestTemplate restTemplate;
	// todo - move in service
	private final MarketItemMapper marketItemMapper;
	private final MarketItemDetailsMapper marketItemDetailsMapper;

	@Autowired
	public AmazonMarketResource(AmazonMarketService amazonMarketService, RestTemplateBuilder restTemplateBuilder,
			MarketItemMapper marketItemMapper, MarketItemDetailsMapper marketItemDetailsMapper) {
		this.amazonMarketService = amazonMarketService;
		this.restTemplate = restTemplateBuilder.build();
		this.marketItemMapper = marketItemMapper;
		this.marketItemDetailsMapper = marketItemDetailsMapper;
	}

	/**
	 * SEARCH /search/products?query=:query : search for the product
	 * corresponding to the query.
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
		return Optional.ofNullable(marketItemDetailsMapper.marketItemToMarketItemDTO(lookeditem)).map(response -> ResponseEntity.ok().headers(header).body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

		// return Optional.ofNullable(resultItems.getItem().get(0))
		// .map(item -> new ResponseEntity<>(new MarketItemDTO(Item),
		// HttpStatus.OK))
		// .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

	}

}
