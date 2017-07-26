package fr.sne.market.service.mapper;

import java.util.List;

import org.mapstruct.*;

import com.amazon.api.ECS.client.jax.Cart;
import com.amazon.api.ECS.client.jax.CartItem;
import com.amazon.api.ECS.client.jax.Item;
import com.amazon.api.ECS.client.jax.Price;

import fr.sne.market.service.dto.CartDTO;
import fr.sne.market.service.dto.CartItemDTO;
import fr.sne.market.service.dto.MarketItemDTO;

@Mapper(componentModel = "spring", uses = {})
public interface CartItemMapper {

	@Mappings({
        @Mapping(source = "cartItemId", target = "cartItemId"),
        @Mapping(source = "asin", target = "asin"),
        @Mapping(source = "quantity", target = "quantity"),
        @Mapping(source = "title", target = "title"),
        @Mapping(source = "productGroup", target = "productGroup"),
        @Mapping(source = "sellerNickname", target = "sellerNickname"),
        @Mapping(source = "price.formattedPrice", target = "unitPrice"),
        @Mapping(source = "itemTotal.formattedPrice", target = "totalCost"),
        
//        Mapping collections
//        @Mapping(source = "cartItems.cartItem", target = "cartItemDTO"),
    })	
	
	CartItemDTO toDTO(CartItem cart);
	
	public List<CartItemDTO> toDTOs(List<CartItem> entityList);
}
