import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { CartService } from '../cart/cart.service';
import { Cart } from '../cart/cart.model';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styles: []
})
export class CartComponent implements OnInit {

  cart: Cart;
//   cartId : '260-9694202-3088431';
//   operation: 'CartGet';
  // hmac: 'N4Zh3pvolpFwRf1ys0KyvUgw1/A=';
  // CartClear
  // CartGet

  constructor(private cartService: CartService,
    private route: ActivatedRoute,
    private location: Location) { }

  ngOnInit(): void {

    this.route.params
      .switchMap((params: Params) => this.cartService.getCart('amazon', params['operation'], params['cartId'],  'N4Zh3pvolpFwRf1ys0KyvUgw1/A=' ))
      .subscribe((cart) => this.cart = cart);

  }

 /**
   * Clear cart
   */
  clearCart(): void {
    this.cartService.getCart('amazon', 'CartClear', '260-9694202-3088431', 'N4Zh3pvolpFwRf1ys0KyvUgw1/A=')
      .then((cart) => this.cart = cart)
      // chaine multiple - composition chianing - promise chaining
      // .then((cart) => this.router.navigate(['/cart', 'CartGet', this.cart.cartId]))
      // error
      .catch((err) => {
        console.log('Error callback', err); // This will NOT be called
      });
    // this.router.navigate(['/product-list'], { queryParams: { page: pageNum } });
  }

goBack(): void {
    this.location.back();
  }

}
