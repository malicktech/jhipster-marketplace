package fr.sne.market.service.mapper;

import java.util.List;

import org.mapstruct.*;

import com.amazon.api.ECS.client.jax.Item;

import fr.sne.market.service.dto.MarketItemDTO;

@Mapper(componentModel = "spring", uses = {})
public interface MarketItemMapper {

	@Mappings({
        @Mapping(source = "asin", target = "asin"),
        @Mapping(source = "itemAttributes.title", target = "title"),
        @Mapping(source = "itemAttributes.label", target = "label"),
        @Mapping(source = "itemAttributes.brand", target = "brand"),
        @Mapping(source = "itemAttributes.model", target = "model"),
        @Mapping(source = "mediumImage.url", target = "mainImageUrl"),
        @Mapping(source = "itemAttributes.listPrice.formattedPrice", target = "price"),
//      @Mapping(target = "mainImageUrl", expression = "java(item1.getMediumImage().getURL())")
    })	
	
	MarketItemDTO marketItemToMarketItemDTO(Item item);
	
	public List <MarketItemDTO> toDto(List<Item> entityList);
}
