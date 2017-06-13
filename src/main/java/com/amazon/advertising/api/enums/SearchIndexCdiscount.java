package com.amazon.advertising.api.enums;

/**
 * List fo Search indices - > product category
 * 
 * @author Malick
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/SearchIndicesandLocales.html
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/LocaleFR.html
 *
 */
public enum SearchIndexCdiscount {

	// Enum objects
	
	All("all", "Toutes nos boutiques"),

	BOOKS("books", "Livres"),

	SPORT("sport", "Sport"),
	
	PHONE("phones", "Téléphone"),
	
	COMPUTERS("computers", "Informatique"),

	CLOTHING("clothing", "Vêtements et accessoires"),

	APPLICANCES("appliances", "Gros électroménager");
/*
KITCHEN("Kitchen", "Cuisine & Maison"),
ELECRONICS("Electronics", "High-Tech"),
PCHARDWARE("PCHardware", "Informatique"),
SHOES("Shoes", "Chaussures et Sacs"), 
MUSICAL_INSTRUMENT("musical instruments", "Instruments de musique & Sono")
	*/

	private String searchindex = "";
	private String department = "";

	// Constructor
	
	SearchIndexCdiscount(String searchindex, String department) {
		this.searchindex = searchindex;
		this.department = department;
	}

	// getters 
	
	public String getDepartment() {
		return this.department;
	}

	public String toString() {
		return this.searchindex;
	}

}
