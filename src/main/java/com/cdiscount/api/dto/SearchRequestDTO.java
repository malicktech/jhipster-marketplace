package com.cdiscount.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @JsonCreator annotation is used for constructors or static factory methods to
 *              construct instances from Json. PARAMETERS have to annotated
 *              by @JsonProperty annotations
 * @JsonProperty annotation is used to bind data by a given name.
 * 
 * 
 * TODO  - use getter and setter in place of constructor ?
 * 
 * @author Malick
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequestDTO {

	@JsonProperty("ApiKey")
	public final String apiKey;

	@JsonProperty("SearchRequest")
	public final SearchRequest searchRequest;

	@JsonCreator
	public SearchRequestDTO(@JsonProperty("ApiKey") String apiKey,
			@JsonProperty("SearchRequest") SearchRequest searchRequest) {
		this.apiKey = apiKey;
		this.searchRequest = searchRequest;
	}

	public static class SearchRequest {
		@JsonProperty("Keyword")
		public final String keyword;
		@JsonProperty("SortBy")
		public final String sortBy;
		@JsonProperty("Pagination")
		public final Pagination pagination;
		@JsonProperty("Filters")
		public final Filters filters;

		@JsonCreator
		public SearchRequest(@JsonProperty("Keyword") String keyword, @JsonProperty("SortBy") String sortBy,
				@JsonProperty("Pagination") Pagination pagination, @JsonProperty("Filters") Filters filters) {
			this.keyword = keyword;
			this.sortBy = sortBy;
			this.pagination = pagination;
			this.filters = filters;
		}

		public static class Pagination {
			@JsonProperty("ItemsPerPage")
			public final long itemsPerPage;
			@JsonProperty("PageNumber")
			public final long pageNumber;

			@JsonCreator
			public Pagination(@JsonProperty("ItemsPerPage") long itemsPerPage,
					@JsonProperty("PageNumber") long pageNumber) {
				this.itemsPerPage = itemsPerPage;
				this.pageNumber = pageNumber;
			}
		}

		public static class Filters {
			@JsonProperty("Price")
			public final Price price;
			@JsonProperty("Navigation")
			public final String navigation;
			@JsonProperty("IncludeMarketPlace")
			public final boolean includeMarketPlace;
//			@JsonProperty("Brands")
//			public final String[] brands;
			// TODO, find a way, like in amazon. regenere dto
			@JsonProperty("Condition")
			public final String condition;

			@JsonCreator
			public Filters(@JsonProperty("Price") Price price, @JsonProperty("Navigation") String navigation 
					,@JsonProperty("IncludeMarketPlace") boolean includeMarketPlace
//					,@JsonProperty("Brands") String[] brands
					,@JsonProperty("Condition") String condition
					) {
				this.price = price;
				this.navigation = navigation;
				this.includeMarketPlace = includeMarketPlace;
//				this.brands = brands;
				this.condition = condition;
			}

			public static class Price {
				@JsonProperty("Min")
				public final long min;
				@JsonProperty("Max")
				public final long max;

				@JsonCreator
				public Price(@JsonProperty("Min") long min, @JsonProperty("Max") long max) {
					this.min = min;
					this.max = max;
				}
			}
		}
	}
}
