package fr.sne.market.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.amazon.api.ECS.client.jax.Item;

import fr.sne.market.service.dto.MarketItemDetailsDTO;

@Mapper(componentModel = "spring", uses = {})
public interface MarketItemDetailsMapper {

	@Mappings({
        @Mapping(source = "asin", target = "asin"),
        @Mapping(source = "itemAttributes.title", target = "title"),
        @Mapping(source = "itemAttributes.label", target = "label"),
        @Mapping(source = "itemAttributes.brand", target = "brand"),
        @Mapping(source = "itemAttributes.model", target = "model"),
        @Mapping(source = "itemAttributes.listPrice.formattedPrice", target = "price"),
		@Mapping(target = "mainImageUrl", expression = "java( item.getMediumImage() != null ? item.getMediumImage().getUrl() : item.getImageSets().get(0).getImageSet().get(0).getMediumImage().getUrl() )"),
		
		@Mapping(source = "itemAttributes.feature", target = "feature"),
		@Mapping(source = "smallImage.url", target = "smallImage"),
		@Mapping(source = "largeImage.url", target = "largeImage"),
		@Mapping(target = "desc", expression = "java( item.getEditorialReviews() != null ? item.getEditorialReviews().getEditorialReview().get(0).getContent() : null )"),

		@Mapping(source = "itemAttributes.author", target = "author"),
		@Mapping(source = "itemAttributes.binding", target = "binding"),
		@Mapping(source = "itemAttributes.color", target = "color"),
		@Mapping(source = "itemAttributes.genre", target = "genre"),

		@Mapping(source = "itemAttributes.productGroup", target = "productGroup"),
		@Mapping(source = "itemAttributes.productTypeName", target = "productTypeName"),
		@Mapping(source = "itemAttributes.productTypeSubcategory", target = "productTypeSubcategory"),
		@Mapping(source = "itemAttributes.publicationDate", target = "publicationDate"),
		@Mapping(source = "itemAttributes.publisher", target = "publisher"),
		@Mapping(source = "itemAttributes.releaseDate", target = "releaseDate"),
		@Mapping(source = "itemAttributes.size", target = "size"),
		@Mapping(source = "itemAttributes.sku", target = "sku"),
		@Mapping(source = "itemAttributes.studio", target = "studio"),		
		
    })	
	
	MarketItemDetailsDTO marketItemToMarketItemDTO(Item item);

}
