import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { ProductService } from '../marketproduct/product.service';
import { Product } from '../marketproduct/product-model';

import { CartService } from '../../amazoncart/cart.service';
import { Cart } from '../../amazoncart/cart.model';

import { CartAddForm } from './cart-add-form';

@Component({
  selector: 'jhi-product-details',
  templateUrl: './product-details.component.html',
  styles: []
})
export class ProductDetailsComponent implements OnInit {

  product: Product;
  cart: Cart;
  model;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location) { }

  ngOnInit(): void {

    this.route.params
      .switchMap((params: Params) => this.productService.getProduct('amazon', params['asin']))
      .subscribe((product) => this.product = product);


    // this.model = new CartAddForm(params['asin'], 1, 'cartAdd');

  }

  goBack(): void {
    this.location.back();
  }

  /**
   * Add to cart
   */
  addToCart(): void {
    this.cartService.addToCart('amazon', 'CartAdd', '260-9694202-3088431', 'N4Zh3pvolpFwRf1ys0KyvUgw1/A=', this.product.asin, 1)
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
