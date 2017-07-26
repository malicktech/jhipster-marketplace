import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommonModule } from '@angular/common';

import { MarketSharedModule } from '../shared';

import {
  MarketproductComponent,
  ProductDetailsComponent,
  CartComponent,
  ProductService,
  CartService,
  amazonitemState
} from './';

@NgModule({
  imports: [
    MarketSharedModule,
    CommonModule,
    RouterModule.forRoot(amazonitemState, { useHash: true })
  ],
  declarations: [
    MarketproductComponent,
    ProductDetailsComponent,
    CartComponent
  ],
  providers: [
    ProductService,
    CartService
  ],
})

export class AmazonitemModule { }
