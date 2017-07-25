import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommonModule } from '@angular/common';

import { MarketSharedModule } from '../shared';

import {
  MarketproductComponent,
  ProductDetailsComponent,
  ProductService,
  amazonitemState
} from './';

@NgModule({
  imports: [
    MarketSharedModule,
    CommonModule,
    RouterModule.forRoot(amazonitemState, { useHash: true }),
    
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
