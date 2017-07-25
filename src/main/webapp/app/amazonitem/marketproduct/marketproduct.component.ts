import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import 'rxjs/add/operator/switchMap';
import { Observable } from 'rxjs/Observable';

import { Product } from './product-model';
import { ProductService } from './product.service';

@Component({
  selector: 'jhi-marketproduct',
  templateUrl: './marketproduct.component.html',
  styles: []
})
export class MarketproductComponent implements OnInit {

products: Product[];
selectedProduct: Product;

  // called first time before the ngOnInit()
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private productService: ProductService
  ) {
    // console.log('constructor');
  }

  // ngOnInit method with the initialization logic inside
  // called after the constructor and called  after the first ngOnChanges()

  ngOnInit(): void {
    // get URL parameters

    // this.route.params
    //   .switchMap((params: Params) => this.productService.getProducts(params['market'], params['searchindex'], params['query'], '1'))
    //   .subscribe(products => this.products = products);

    // console.log('market');

    this.route.params.subscribe((params) => {
      // if (params['market']) {
      //   console.log(params['market']);
      // }
      //     this.productService.getProducts(market, searchindex, query, '1')
      //     .then(products => {this.products = products;});
      //   });
      const market = params['market'] || 'amazon';
      const searchindex = params['searchindex'] || 'Books';
      const query = params['query'] || 'diome';
      const itempage = params['itempage'] || '1';

      this.productService.getProducts(market, searchindex, query, itempage)
        .then((products) => { this.products = products; });

    });
  }

  // use Promises, To coordinate the view with the response,
  // Pass the callback function as an argument to the Promise's then() method:
  getHeroes(market: string, searchindex: string, query: string, itempage: string): void {
    this.productService.getProducts(market, searchindex, query, itempage).then((products) => this.products = products); // call the service and get the data in one line
  }

  // goToPage(pageNum) {
  //   this.router.navigate(['/product-list'], { queryParams: { page: pageNum } });
  // }

/*
  gotoDetail(): void {
    this.router.navigate(['/marketproduct', this.selectedProduct.asin]);
  }
  */
}
