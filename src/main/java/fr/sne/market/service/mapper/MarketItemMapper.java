package fr.sne.market.service.mapper;

import java.util.List;

import org.mapstruct.*;

import com.amazon.api.ECS.client.jax.Item;

import fr.sne.market.service.dto.MarketItemDTO;

@Mapper(componentModel = "spring", uses = {})
public interface MarketItemMapper {

	@Mappings({ @Mapping(source = "asin", target = "asin"), @Mapping(source = "itemAttributes.title", target = "title"),
			// Conditional mapping Create mainImage from data in mediumImage
			// if mediumImage is not null then map mediumImage , else map
			// ImageSet variant only.
			// @Mapping(source = "mediumImage.url", target = "mainImageUrl"),
			@Mapping(target = "mainImageUrl", expression = "java( item.getMediumImage() != null ? item.getMediumImage().getUrl() : item.getImageSets().get(0).getImageSet().get(0).getMediumImage().getUrl() )"),

			@Mapping(source = "itemAttributes.listPrice.formattedPrice", target = "price"),
			@Mapping(source = "offerSummary.lowestNewPrice.formattedPrice", target = "lowestNewPrice"),

			// @Mapping(source =
			// "itemAttributes.editorialReviews.editorialReview", target =
			// "desc"),
			@Mapping(source = "itemAttributes.brand", target = "brand"),
			@Mapping(source = "itemAttributes.model", target = "model"),

	})

	MarketItemDTO marketItemToMarketItemDTO(Item item);

	public List<MarketItemDTO> toDto(List<Item> entityList);

}
