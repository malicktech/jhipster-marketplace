import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommonModule } from '@angular/common';

import { MarketSharedModule } from '../shared';

import { marketprodutRoute } from './marketproduct/marketproduct.route'; marketprodutRoute
import { MarketproductComponent } from './marketproduct/marketproduct.component';


@NgModule({
  imports: [
    MarketSharedModule,
    CommonModule,
    RouterModule.forRoot([marketprodutRoute], { useHash: true })
  ],
  declarations: [
    MarketproductComponent
  ]
})
export class AmazonitemModule { }
