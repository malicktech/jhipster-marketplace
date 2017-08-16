package fr.sne.market.service.mapper;

import com.amazon.api.ECS.client.jax.CartItem;

import com.amazon.api.ECS.client.jax.Price;

import fr.sne.market.service.dto.CartItemDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-08-16T17:47:58+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_131 (Oracle Corporation)"

)

@Component

public class CartItemMapperImpl implements CartItemMapper {

    @Override

    public CartItemDTO toDTO(CartItem cart) {

        if ( cart == null ) {

            return null;
        }

        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setUnitPrice( cartPriceFormattedPrice( cart ) );

        cartItemDTO.setQuantity( cart.getQuantity() );

        cartItemDTO.setProductGroup( cart.getProductGroup() );

        cartItemDTO.setSellerNickname( cart.getSellerNickname() );

        cartItemDTO.setAsin( cart.getAsin() );

        cartItemDTO.setTitle( cart.getTitle() );

        cartItemDTO.setTotalCost( cartItemTotalFormattedPrice( cart ) );

        cartItemDTO.setCartItemId( cart.getCartItemId() );

        return cartItemDTO;
    }

    @Override

    public List<CartItemDTO> toDTOs(List<CartItem> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<CartItemDTO> list = new ArrayList<CartItemDTO>();

        for ( CartItem cartItem : entityList ) {

            list.add( toDTO( cartItem ) );
        }

        return list;
    }

    private String cartPriceFormattedPrice(CartItem cartItem) {

        if ( cartItem == null ) {

            return null;
        }

        Price price = cartItem.getPrice();

        if ( price == null ) {

            return null;
        }

        String formattedPrice = price.getFormattedPrice();

        if ( formattedPrice == null ) {

            return null;
        }

        return formattedPrice;
    }

    private String cartItemTotalFormattedPrice(CartItem cartItem) {

        if ( cartItem == null ) {

            return null;
        }

        Price itemTotal = cartItem.getItemTotal();

        if ( itemTotal == null ) {

            return null;
        }

        String formattedPrice = itemTotal.getFormattedPrice();

        if ( formattedPrice == null ) {

            return null;
        }

        return formattedPrice;
    }
}

