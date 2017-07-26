package fr.sne.market.service.mapper;

import java.util.List;

import org.mapstruct.*;

import com.amazon.api.ECS.client.jax.Cart;
import com.amazon.api.ECS.client.jax.Item;
import com.amazon.api.ECS.client.jax.Price;

import fr.sne.market.service.dto.CartDTO;
import fr.sne.market.service.dto.CartItemDTO;
import fr.sne.market.service.dto.MarketItemDTO;

@Mapper(componentModel = "spring", uses = {})
public interface CartMapper {

	@Mappings({
        @Mapping(source = "cartId", target = "cartId"),
        @Mapping(source = "hmac", target = "hmac"),
        @Mapping(source = "purchaseURL", target = "purchaseURL"),
        @Mapping(source = "subTotal.formattedPrice", target = "subTotal"),
//        Mapping collections
//        @Mapping(source = "cartItems.cartItem", target = "cartItemDTO"),
    })	
	
	CartDTO cartToCartDTO(Cart cart);
	
	public List <CartDTO> cartsToCartDTOs(List<Cart> entityList);
}
