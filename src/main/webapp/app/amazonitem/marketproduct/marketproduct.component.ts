import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { LocalStorageService, SessionStorageService } from 'ng2-webstorage';

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

  sessionCartId: String;
  sessionCartIdHmac: String;

  cartLinkEnabled: boolean;

  // called first time before the ngOnInit()
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private productService: ProductService,
    private localStorage: LocalStorageService,
    private sessionStorage: SessionStorageService
  ) {
    console.log('MarketproductComponent constructor');
    route.params.subscribe(val => this.ngOnInit())
  }

  // ngOnInit method with the initialization logic inside
  // called after the constructor and called  after the first ngOnChanges()

  ngOnInit(): void {
    this.sessionCartId = this.localStorage.retrieve('sessionCartId') || this.sessionStorage.retrieve('sessionCartId');
    if (!!this.sessionCartId) {
      console.log('sessionCartId :' + this.sessionCartId);
    }

    this.sessionCartIdHmac = this.localStorage.retrieve('sessionCartIdHmac') || this.sessionStorage.retrieve('sessionCartIdHmac');

    this.cartLinkEnabled = (!!this.sessionCartId && !!this.sessionCartIdHmac) ? true : false;

    // each time the search data is change you'll get this running
    //Do what ever you need to refresh your search page
    console.log('MarketproductComponent ngOnInit - New route params');


    // get URL parameters

    // this.route.params
    //   .switchMap((params: Params) => this.productService.getProducts(params['market'], params['searchindex'], params['query'], '1'))
    //   .subscribe(products => this.products = products);


    // this.route.params
    //       .switchMap((params: Params) => this.productService.getProduct('amazon', params['asin']))
    //       .subscribe((product) => this.product = product);

    this.route.params.subscribe((params) => {
      // if (params['market']) {
      //   console.log(params['market']);
      // }
      //     this.productService.getProducts(market, searchindex, query, '1')
      //     .then(products => {this.products = products;});
      //   });

      console.log('MarketproductComponent subscribe');
      // this.resetComponentState(); // based on new parameter this time

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
    this.productService.getProducts(market, searchindex, query, itempage)
      .then((products) => this.products = products); // call the service and get the data in one line
  }

  goBack(): void {
    this.location.back();
  }

  // goToPage(pageNum : string) {
  //   this.router.navigate(['/marketproduct'], { queryParams: { page: pageNum } });
  // }

  /*
    gotoDetail(): void {
      this.router.navigate(['/marketproduct', this.selectedProduct.asin]);
    }
    */
}
