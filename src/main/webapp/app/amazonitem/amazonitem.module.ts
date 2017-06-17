import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommonModule } from '@angular/common';

import { MarketSharedModule } from '../shared';

// import { marketprodutRoute } from './marketproduct/marketproduct.route';
// import { ProductDetailsRoute } from './product-details/product-details.route';
// import { MarketproductComponent } from './marketproduct/marketproduct.component';

// import { ProductService } from './marketproduct/product.service';
// import { ProductDetailsComponent } from './product-details/product-details.component';

import {
  MarketproductComponent,
  marketprodutRoute,
  ProductDetailsComponent,
  ProductDetailsRoute,
  ProductService,
  amazonitemState
} from './';

@NgModule({
  imports: [
    MarketSharedModule,
    CommonModule,
    RouterModule.forRoot([amazonitemState], { useHash: true })
  ],
  declarations: [
    MarketproductComponent,
    ProductDetailsComponent
  ],
  providers: [
    ProductService
  ],
})

export class AmazonitemModule { }
