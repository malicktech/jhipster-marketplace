package fr.sne.market.service.mapper;

import com.amazon.api.ECS.client.jax.Item;

import com.amazon.api.ECS.client.jax.ItemAttributes;

import com.amazon.api.ECS.client.jax.OfferSummary;

import com.amazon.api.ECS.client.jax.Price;

import fr.sne.market.service.dto.MarketItemDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-08-18T19:25:22+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_131 (Oracle Corporation)"

)

@Component

public class MarketItemMapperImpl implements MarketItemMapper {

    @Override

    public MarketItemDTO marketItemToMarketItemDTO(Item item) {

        if ( item == null ) {

            return null;
        }

        MarketItemDTO marketItemDTO = new MarketItemDTO();

        marketItemDTO.setPrice( itemItemAttributesListPriceFormattedPrice( item ) );

        marketItemDTO.setLowestNewPrice( itemOfferSummaryLowestNewPriceFormattedPrice( item ) );

        marketItemDTO.setAsin( item.getAsin() );

        marketItemDTO.setModel( itemItemAttributesModel( item ) );

        marketItemDTO.setTitle( itemItemAttributesTitle( item ) );

        marketItemDTO.setBrand( itemItemAttributesBrand( item ) );

        marketItemDTO.setMainImageUrl( item.getMediumImage() != null ? item.getMediumImage().getUrl() : item.getImageSets().get(0).getImageSet().get(0).getMediumImage().getUrl() );

        return marketItemDTO;
    }

    @Override

    public List<MarketItemDTO> toDto(List<Item> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<MarketItemDTO> list = new ArrayList<MarketItemDTO>();

        for ( Item item : entityList ) {

            list.add( marketItemToMarketItemDTO( item ) );
        }

        return list;
    }

    private String itemItemAttributesListPriceFormattedPrice(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        Price listPrice = itemAttributes.getListPrice();

        if ( listPrice == null ) {

            return null;
        }

        String formattedPrice = listPrice.getFormattedPrice();

        if ( formattedPrice == null ) {

            return null;
        }

        return formattedPrice;
    }

    private String itemOfferSummaryLowestNewPriceFormattedPrice(Item item) {

        if ( item == null ) {

            return null;
        }

        OfferSummary offerSummary = item.getOfferSummary();

        if ( offerSummary == null ) {

            return null;
        }

        Price lowestNewPrice = offerSummary.getLowestNewPrice();

        if ( lowestNewPrice == null ) {

            return null;
        }

        String formattedPrice = lowestNewPrice.getFormattedPrice();

        if ( formattedPrice == null ) {

            return null;
        }

        return formattedPrice;
    }

    private String itemItemAttributesModel(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String model = itemAttributes.getModel();

        if ( model == null ) {

            return null;
        }

        return model;
    }

    private String itemItemAttributesTitle(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String title = itemAttributes.getTitle();

        if ( title == null ) {

            return null;
        }

        return title;
    }

    private String itemItemAttributesBrand(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String brand = itemAttributes.getBrand();

        if ( brand == null ) {

            return null;
        }

        return brand;
    }
}

