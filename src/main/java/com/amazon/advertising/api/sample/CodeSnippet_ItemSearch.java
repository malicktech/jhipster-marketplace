package com.amazon.advertising.api.sample;

import java.util.HashMap;
import java.util.Map;

import com.amazon.advertising.api.helpers.SignedRequestsHelper;


/*
 * This class shows how to make a simple authenticated call to the
 * Amazon Product Advertising API.
 *
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */

public class CodeSnippet_ItemSearch {
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
    
    public static void main(String[] args) {

        /*
         * Set up the signed requests helper.
         */
        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String requestUrl = null;

        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", "AKIAJ5XZFNFFPAGZEDFQ");
        params.put("AssociateTag", "daktic-21");
        params.put("SearchIndex", "Books");
        params.put("ResponseGroup", "Images,ItemAttributes,Medium,Offers");
        params.put("Keywords", "Css PHP");

        requestUrl = helper.sign(params);

        System.out.println("Signed URL: \"" + requestUrl + "\"");
    }
}