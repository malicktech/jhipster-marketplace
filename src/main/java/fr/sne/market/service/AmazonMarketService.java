package fr.sne.market.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazon.advertising.api.enums.SearchIndex;
import com.amazon.advertising.api.helpers.SignedRequestsHelper;


/**
 * Service class for managing Amazon Items.
 */
@Service
public class AmazonMarketService {

	private final Logger log = LoggerFactory.getLogger(AmazonMarketService.class);
	
	/*
	 * Your AWS Access Key ID, as taken from the AWS Your Account page.
	 */
	private static final String AWS_ACCESS_KEY_ID = "AKIAJ5XZFNFFPAGZEDFQ";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String AWS_SECRET_KEY = "SA8O6zNW9JvhdEsKdvECeUmAPwywo+EqcVBMYr6D";

	/*
	 * Use the end-point according to the region you are interested in.
	 */
	private static final String ENDPOINT = "webservices.amazon.fr";
	
	SignedRequestsHelper helper;
	{
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final RestTemplate restTemplate;

	public AmazonMarketService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	
	/**
	 * ItemSearch returns up to 10 search results per page
	 */
	public String itemSearch(String keywords, String searchindex, String itemPage) {
		
		SignedRequestsHelper helper;

		log.info(Arrays.asList(SearchIndex.values()).toString());
//		log.info(SearchIndex.BOOKS.name()); // BOOKS
//		log.info(SearchIndex.BOOKS.toString()); // Books
		log.info("keywords = " + keywords + " | searchindex = " + searchindex);
		
		try {
			// TODO - use instancied this.helper
			helper = SignedRequestsHelper.getInstance("webservices.amazon.fr", "AKIAJ5XZFNFFPAGZEDFQ",
					"SA8O6zNW9JvhdEsKdvECeUmAPwywo+EqcVBMYr6D");
			String requestUrl = null;

			Map<String, String> params = new HashMap<String, String>();
			params.put("Service", "AWSECommerceService");
			params.put("Operation", "ItemSearch");
			params.put("AWSAccessKeyId", "AKIAJ5XZFNFFPAGZEDFQ");
			params.put("AssociateTag", "daktic-21");
			
			// The product category to search
			params.put("SearchIndex", searchindex);
			
			// Filters search results and offer listings to only items sold by Amazon.
			params.put("MerchantId", "Amazon");
			
			// Returns items by condition type
	        params.put("Condition", "New");
			
			// The type of product data you want returned
			// http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CHAP_ResponseGroupsList.html
			/**
			 * Images : images
			 * ItemAttributes :
			 * Offers : prices
			 */
	        params.put("ResponseGroup", "Images, ItemAttributes, Offers");
			
			// A word or phrase that describes an item, such as a title, author, and so on.
			params.put("Keywords", keywords);
			
			// Enables the ItemSearch operation to return only available items.
			params.put("Availability", "Available");
			
			// Returns a specific page of items from all of the items in a response.
			params.put("ItemPage", itemPage);
			
			// How items in the response are ordered. 
			// specify the order of the items in a response.
			// default , not use the BrowseNode parameter : relevancerank
			// possible value : Relevance / BestSeller
			
			if (searchindex.equalsIgnoreCase("Books")) {
				
				params.put("Sort", "titlerank");
				// price
			}
			
			// Return signed request
			requestUrl = helper.sign(params);
			// log.info("Signed URL: \"" + requestUrl + "\"");
			System.out.println(requestUrl);
			return requestUrl;

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * ItemLookup returns Product Details
	 */
	public String itemLookup(String asin) {		
		/*
         * Set up the signed requests helper.
         */
		SignedRequestsHelper helper;

		try {
			helper = SignedRequestsHelper.getInstance("webservices.amazon.fr", "AKIAJ5XZFNFFPAGZEDFQ",
					"SA8O6zNW9JvhdEsKdvECeUmAPwywo+EqcVBMYr6D");			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		String requestUrl = null;

        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemLookup");
        params.put("AWSAccessKeyId", "AKIAJ5XZFNFFPAGZEDFQ");
        params.put("AssociateTag", "daktic-21");
        params.put("ItemId", asin);
        params.put("IdType", "ASIN");
        
        // The type of product data you want returned
        // params.put("ResponseGroup", "EditorialReview,Large,OfferFull,Offers,OfferSummary,Reviews,SalesRank");
        params.put("ResponseGroup", "Images, ItemAttributes, Offers");
        // params.put("ResponseGroup", "Large");
        
        requestUrl = helper.sign(params);
        
        log.info("Signed URL: \"" + requestUrl + "\"");
        return requestUrl;

	}
	
	/**
	 * 
	 * The CartGet operation returns the IDs, quantities, and prices of all items in a remote shopping cart
	 * 
	 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartGet.html
	 * 
	 */
	public String getCartSample(String cartId, String hmac, String responseGroup, String operation) {

		String requestUrl = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", operation); // CartGet
		params.put("AWSAccessKeyId", "AKIAJ5XZFNFFPAGZEDFQ");
		params.put("AssociateTag", "daktic-21");

		params.put("CartId", cartId);
		params.put("HMAC", hmac);
		params.put("ResponseGroup", responseGroup);

		requestUrl = helper.sign(params);

		log.info("Signed URL: \"" + requestUrl + "\"");
		return requestUrl;
	}

	/**
	 * The CartCreate operation creates a remote shopping cart
	 * 
	 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartCreate.html
	 * 
	 * @param asin = The Amazon Standard Identification Number of an item.
	 * @param quantity = The number of items to add to cart.
	 * @param responseGroup = The type of product data you want returned
	 * 
	 * @return signed URL
	 */
	public String createCartSample(String asin, String quantity, String responseGroup) {

		String requestUrl = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "CartCreate");
		params.put("AWSAccessKeyId", "AKIAJ5XZFNFFPAGZEDFQ");
		params.put("AssociateTag", "daktic-21");

		params.put("Item.1.ASIN", asin);
		params.put("Item.1.Quantity", quantity);
		
		params.put("ResponseGroup", responseGroup);

		requestUrl = helper.sign(params);

		log.info("Signed URL: \"" + requestUrl + "\"");
		return requestUrl;
	}

		/**
		 * The CartAdd operation enables you to add items to an existing remote shopping cart
		 * 
		 * @param cartId
		 * @param hmac
		 * @param asin
		 * @param quantity
		 * @param responseGroup
		 * @param operation
		 * @return
		 */
	public String cartAddSample(String cartId, String hmac, String asin, String quantity, String responseGroup,
			String operation) {
		String requestUrl = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", operation); // CartAdd
		params.put("AWSAccessKeyId", "AKIAJ5XZFNFFPAGZEDFQ");
		params.put("AssociateTag", "daktic-21");

		params.put("CartId", cartId);
		params.put("HMAC", hmac);
		params.put("Item.1.Quantity", quantity); // Item-1-ASIN not work

		if (operation.equalsIgnoreCase("CartAdd")) {
			params.put("Item.1.ASIN", asin);
		} else {
			params.put("Item.1.CartItemId", asin);
		}

		// CURRENTLY ON ITEM add by time? POSSIBLE TO ADD many in same time
		// params.put("Item.2.ASIN", asin);
		// params.put("Item.2.Quantity", quantity);

		params.put("ResponseGroup", responseGroup);

		requestUrl = helper.sign(params);

		log.info("Signed URL: \"" + requestUrl + "\"");
		return requestUrl;
	}
	
	
	/**
	 * TODO 
	 * Get List Panier ,
	 * id saved in backoffice
	 */
	public void getListCard() {
		
	}
	
}
