import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { LocalStorageService, SessionStorageService } from 'ng2-webstorage';

import { CartService } from './cart.service';
import { Cart } from './cart.model';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styles: []
})
export class CartComponent implements OnInit {

  cart: Cart;
  cartId: String;
  operation: String;
  hmac: String;
  sessionCartId: String;
  sessionCartIdHmac: String;


  constructor(private cartService: CartService,
    // private route: ActivatedRoute,
    private location: Location,
    private localStorage: LocalStorageService,
    private sessionStorage: SessionStorageService
  ) { }

  ngOnInit(): void {
    
    this.sessionCartId = this.localStorage.retrieve('sessionCartId') || this.sessionStorage.retrieve('sessionCartId');
    if (!!this.sessionCartId) {
      console.log('sessionCartId :' + this.sessionCartId);
    }
    this.sessionCartIdHmac = this.localStorage.retrieve('sessionCartIdHmac') || this.sessionStorage.retrieve('sessionCartIdHmac');
    if (!!this.sessionCartId) {
      console.log('sessionCartIdHmac :' + this.sessionCartIdHmac);
    }

    this.operation = 'CartGet';

    this.cartService.getCart('amazon', this.operation, this.sessionCartId, this.sessionCartIdHmac)
      .then((cart) => { this.cart = cart; });

  }

  /**
    * Clear cart
    */
  clearCart(): void {
    this.cartService.getCart('amazon', 'CartClear', this.sessionCartId, this.sessionCartIdHmac)
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
