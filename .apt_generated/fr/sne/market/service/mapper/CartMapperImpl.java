package fr.sne.market.service.mapper;

import com.amazon.api.ECS.client.jax.Cart;

import com.amazon.api.ECS.client.jax.Price;

import fr.sne.market.service.dto.CartDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-07-26T15:45:31+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_131 (Oracle Corporation)"

)

@Component

public class CartMapperImpl implements CartMapper {

    @Override

    public CartDTO cartToCartDTO(Cart cart) {

        if ( cart == null ) {

            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setPurchaseURL( cart.getPurchaseURL() );

        cartDTO.setSubTotal( cartSubTotalFormattedPrice( cart ) );

        cartDTO.setCartId( cart.getCartId() );

        cartDTO.setHmac( cart.getHmac() );

        return cartDTO;
    }

    @Override

    public List<CartDTO> cartsToCartDTOs(List<Cart> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<CartDTO> list = new ArrayList<CartDTO>();

        for ( Cart cart : entityList ) {

            list.add( cartToCartDTO( cart ) );
        }

        return list;
    }

    private String cartSubTotalFormattedPrice(Cart cart) {

        if ( cart == null ) {

            return null;
        }

        Price subTotal = cart.getSubTotal();

        if ( subTotal == null ) {

            return null;
        }

        String formattedPrice = subTotal.getFormattedPrice();

        if ( formattedPrice == null ) {

            return null;
        }

        return formattedPrice;
    }
}

