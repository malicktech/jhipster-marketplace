export class Cart {
  cartId: string;
  hmac: string;
  purchaseURL: string;
  subTotal: string;
  cartItem: CartItem[];
}
export class CartItem {
  cartItemId: string;
  asin: string;
  sellerNickname: string;
  quantity: string;
  title: string;
  productGroup: string;
  unitPrice: string;
  totalCost: string;
}
