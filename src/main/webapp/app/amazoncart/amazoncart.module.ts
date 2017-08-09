import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../shared';
import { AmazonitemModule } from '../amazonitem';

import { CartComponent, CartService, CART_ROUTE } from './';

@NgModule({
  imports: [
    MarketSharedModule,
    // AmazonitemModule,
    RouterModule.forRoot([ CART_ROUTE ], { useHash: true })
  ],
  declarations: [
    CartComponent
  ],
  entryComponents: [
  ],
  providers: [
    CartService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmazonCartModule { }

