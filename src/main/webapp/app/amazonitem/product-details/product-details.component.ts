import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { ProductService } from '../marketproduct/product.service';
import { Product } from '../marketproduct/product-model';

import { CartService } from '../../amazoncart/cart.service';
import { Cart } from '../../amazoncart/cart.model';

import { CartAddForm } from './cart-add-form';

import { LocalStorageService, SessionStorageService } from 'ng2-webstorage';


@Component({
  selector: 'jhi-product-details',
  templateUrl: './product-details.component.html',
  styles: []
})
export class ProductDetailsComponent implements OnInit {

  product: Product;
  cart: Cart;
  sessionCartId: String;
  sessionCartIdHmac: String;
  quantity: number = 1;


  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private localStorage: LocalStorageService,
    private sessionStorage: SessionStorageService) { }

  ngOnInit(): void {

    this.route.params
      .switchMap((params: Params) => this.productService.getProduct('amazon', params['asin']))
      .subscribe((product) => this.product = product);

    this.sessionCartId = this.localStorage.retrieve('sessionCartId') || this.sessionStorage.retrieve('sessionCartId');
    if (!!this.sessionCartId) {
      console.log('sessionCartId :' + this.sessionCartId);
    }

    this.sessionCartIdHmac = this.localStorage.retrieve('sessionCartIdHmac') || this.sessionStorage.retrieve('sessionCartIdHmac');
    if (!!this.sessionCartId) {
      console.log('sessionCartIdHmac :' + this.sessionCartIdHmac);
    }

    // localStorage.setItem('currentUser', JSON.stringify({ token: token, name: name }));
    // user = JSON.parse(localStorage.getItem(currentUser));
  }

  /**
   * Add to cart
   */
  addToCart(): void {
    if (!this.sessionCartId && !this.sessionCartIdHmac) {
      this.createCart();
    }
    else {
      this.cartService
        .addToCart('amazon', 'CartAdd', this.sessionCartId, this.sessionCartIdHmac, this.product.asin, this.quantity)
        .then((cart) => this.cart = cart)
        // chaine multiple - composition chianing - promise chaining
        // .then((cart) => this.router.navigate(['/cart', 'CartGet', this.cart.cartId])) // rigth way
        .then((cart) => this.router.navigate(['/cart']))
        // error
        .catch((err) => {
          console.log('Error callback', err); // This will NOT be called
        });
      // this.router.navigate(['/product-list'], { queryParams: { page: pageNum } });
    }
  }
  /**
   * Create cart
   */
  createCart(): void {
    this.cartService
      .createCart('amazon', 'CartCreate', this.product.asin, this.quantity)
      .then((cart) => {
        this.cart = cart;
        this.localStorage.store('sessionCartId', cart.cartId);
        this.localStorage.store('sessionCartIdHmac', cart.hmac);
      })
      .then((cart) => this.router.navigate(['/cart']))
      .catch((err) => {
        console.log('Error callback', err);
      });
  }

  goBack(): void {
    this.location.back();
  }

  plus() {
    this.quantity = this.quantity + 1;
  }
  minus() {
    this.quantity = this.quantity - 1;
  }

}
