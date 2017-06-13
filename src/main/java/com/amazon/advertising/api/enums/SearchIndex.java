package com.amazon.advertising.api.enums;

/**
 * List fo Search indices - > product category
 * 
 * @author Malick
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/SearchIndicesandLocales.html
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/LocaleFR.html
 *
 */
public enum SearchIndex {

	// Enum objects
	
	All("All", "Toutes nos boutiques"),

	BOOKS("Books", "Livres en français"),

	FOREIGNBOOKS("ForeignBooks", "Livres anglais et étrangers"),

	APPAREL("Apparel", "Vêtements et accessoires"),

	APPLICANCES("Appliances", "Gros électroménager"),

	KITCHEN("Kitchen", "Cuisine & Maison"),

	ELECRONICS("Electronics", "High-Tech"),

	PCHARDWARE("PCHardware", "Informatique"),

	SHOES("Shoes", "Chaussures et Sacs"),

	MUSICAL_INSTRUMENT("MusicalInstruments", "Instruments de musique & Sono");

	private String searchindex = "";
	private String department = "";

	// Constructor
	
	SearchIndex(String searchindex, String department) {
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
