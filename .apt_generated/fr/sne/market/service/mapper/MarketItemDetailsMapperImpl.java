package fr.sne.market.service.mapper;

import com.amazon.api.ECS.client.jax.Image;

import com.amazon.api.ECS.client.jax.Item;

import com.amazon.api.ECS.client.jax.ItemAttributes;

import com.amazon.api.ECS.client.jax.Price;

import fr.sne.market.service.dto.MarketItemDetailsDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-08-18T19:47:33+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_131 (Oracle Corporation)"

)

@Component

public class MarketItemDetailsMapperImpl implements MarketItemDetailsMapper {

    @Override

    public MarketItemDetailsDTO marketItemToMarketItemDTO(Item item) {

        if ( item == null ) {

            return null;
        }

        MarketItemDetailsDTO marketItemDetailsDTO = new MarketItemDetailsDTO();

        marketItemDetailsDTO.setStudio( itemItemAttributesStudio( item ) );

        marketItemDetailsDTO.setLargeImage( itemLargeImageUrl( item ) );

        marketItemDetailsDTO.setSmallImage( itemSmallImageUrl( item ) );

        marketItemDetailsDTO.setProductTypeSubcategory( itemItemAttributesProductTypeSubcategory( item ) );

        marketItemDetailsDTO.setColor( itemItemAttributesColor( item ) );

        marketItemDetailsDTO.setBinding( itemItemAttributesBinding( item ) );

        marketItemDetailsDTO.setTitle( itemItemAttributesTitle( item ) );

        List<String> list = itemItemAttributesFeature( item );

        if ( list != null ) {

            marketItemDetailsDTO.setFeature(       new ArrayList<String>( list )

            );
        }

        marketItemDetailsDTO.setPrice( itemItemAttributesListPriceFormattedPrice( item ) );

        marketItemDetailsDTO.setGenre( itemItemAttributesGenre( item ) );

        marketItemDetailsDTO.setProductTypeName( itemItemAttributesProductTypeName( item ) );

        marketItemDetailsDTO.setModel( itemItemAttributesModel( item ) );

        marketItemDetailsDTO.setSku( itemItemAttributesSku( item ) );

        marketItemDetailsDTO.setBrand( itemItemAttributesBrand( item ) );

        marketItemDetailsDTO.setPublicationDate( itemItemAttributesPublicationDate( item ) );

        marketItemDetailsDTO.setReleaseDate( itemItemAttributesReleaseDate( item ) );

        List<String> list_ = itemItemAttributesAuthor( item );

        if ( list_ != null ) {

            marketItemDetailsDTO.setAuthor(       new ArrayList<String>( list_ )

            );
        }

        marketItemDetailsDTO.setLabel( itemItemAttributesLabel( item ) );

        marketItemDetailsDTO.setSize( itemItemAttributesSize( item ) );

        marketItemDetailsDTO.setProductGroup( itemItemAttributesProductGroup( item ) );

        marketItemDetailsDTO.setPublisher( itemItemAttributesPublisher( item ) );

        marketItemDetailsDTO.setAsin( item.getAsin() );

        marketItemDetailsDTO.setMainImageUrl( item.getMediumImage() != null ? item.getMediumImage().getUrl() : item.getImageSets().get(0).getImageSet().get(0).getMediumImage().getUrl() );

        marketItemDetailsDTO.setDesc( item.getEditorialReviews() != null ? item.getEditorialReviews().getEditorialReview().get(0).getContent() : null );

        return marketItemDetailsDTO;
    }

    private String itemItemAttributesStudio(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String studio = itemAttributes.getStudio();

        if ( studio == null ) {

            return null;
        }

        return studio;
    }

    private String itemLargeImageUrl(Item item) {

        if ( item == null ) {

            return null;
        }

        Image largeImage = item.getLargeImage();

        if ( largeImage == null ) {

            return null;
        }

        String url = largeImage.getUrl();

        if ( url == null ) {

            return null;
        }

        return url;
    }

    private String itemSmallImageUrl(Item item) {

        if ( item == null ) {

            return null;
        }

        Image smallImage = item.getSmallImage();

        if ( smallImage == null ) {

            return null;
        }

        String url = smallImage.getUrl();

        if ( url == null ) {

            return null;
        }

        return url;
    }

    private String itemItemAttributesProductTypeSubcategory(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String productTypeSubcategory = itemAttributes.getProductTypeSubcategory();

        if ( productTypeSubcategory == null ) {

            return null;
        }

        return productTypeSubcategory;
    }

    private String itemItemAttributesColor(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String color = itemAttributes.getColor();

        if ( color == null ) {

            return null;
        }

        return color;
    }

    private String itemItemAttributesBinding(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String binding = itemAttributes.getBinding();

        if ( binding == null ) {

            return null;
        }

        return binding;
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

    private List<String> itemItemAttributesFeature(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        List<String> feature = itemAttributes.getFeature();

        if ( feature == null ) {

            return null;
        }

        return feature;
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

    private String itemItemAttributesGenre(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String genre = itemAttributes.getGenre();

        if ( genre == null ) {

            return null;
        }

        return genre;
    }

    private String itemItemAttributesProductTypeName(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String productTypeName = itemAttributes.getProductTypeName();

        if ( productTypeName == null ) {

            return null;
        }

        return productTypeName;
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

    private String itemItemAttributesSku(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String sku = itemAttributes.getSku();

        if ( sku == null ) {

            return null;
        }

        return sku;
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

    private String itemItemAttributesPublicationDate(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String publicationDate = itemAttributes.getPublicationDate();

        if ( publicationDate == null ) {

            return null;
        }

        return publicationDate;
    }

    private String itemItemAttributesReleaseDate(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String releaseDate = itemAttributes.getReleaseDate();

        if ( releaseDate == null ) {

            return null;
        }

        return releaseDate;
    }

    private List<String> itemItemAttributesAuthor(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        List<String> author = itemAttributes.getAuthor();

        if ( author == null ) {

            return null;
        }

        return author;
    }

    private String itemItemAttributesLabel(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String label = itemAttributes.getLabel();

        if ( label == null ) {

            return null;
        }

        return label;
    }

    private String itemItemAttributesSize(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String size = itemAttributes.getSize();

        if ( size == null ) {

            return null;
        }

        return size;
    }

    private String itemItemAttributesProductGroup(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String productGroup = itemAttributes.getProductGroup();

        if ( productGroup == null ) {

            return null;
        }

        return productGroup;
    }

    private String itemItemAttributesPublisher(Item item) {

        if ( item == null ) {

            return null;
        }

        ItemAttributes itemAttributes = item.getItemAttributes();

        if ( itemAttributes == null ) {

            return null;
        }

        String publisher = itemAttributes.getPublisher();

        if ( publisher == null ) {

            return null;
        }

        return publisher;
    }
}

