import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../shared';

import {
  MarketproductComponent,
  ProductDetailsComponent,
  ProductService,
  amazonitemState,
  SearchFormComponent

} from './';

@NgModule({
  imports: [
    MarketSharedModule,
    RouterModule.forRoot(amazonitemState, { useHash: true })
  ],
  declarations: [
    MarketproductComponent,
    ProductDetailsComponent,
    SearchFormComponent,
  ],
  providers: [
    ProductService,
  ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class AmazonitemModule { }
