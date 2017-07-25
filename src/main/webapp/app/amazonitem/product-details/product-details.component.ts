import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { ProductService } from '../marketproduct/product.service';
import { Product } from '../marketproduct/product-model';

@Component({
  selector: 'jhi-product-details',
  templateUrl: './product-details.component.html',
  styles: []
})
export class ProductDetailsComponent implements OnInit {

  product: Product;

  constructor(private productService: ProductService,
    private route: ActivatedRoute,
    private location: Location) { }

  ngOnInit(): void {

    this.route.params
      .switchMap((params: Params) => this.productService.getProduct('amazon', params['asin']))
      .subscribe((product) => this.product = product);
  }

goBack(): void {
    this.location.back();
  }

}
