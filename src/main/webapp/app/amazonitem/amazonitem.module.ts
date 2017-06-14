import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommonModule } from '@angular/common';

import { MarketSharedModule } from '../shared';

import { marketprodutRoute } from './marketproduct/marketproduct.route'; marketprodutRoute
import { MarketproductComponent } from './marketproduct/marketproduct.component';
import { ProductSearchFormComponent } from './marketsearchform/search-form.component';

import { ProductService } from './marketproduct/product.service';


@NgModule({
  imports: [
    MarketSharedModule,
    CommonModule,
    RouterModule.forRoot([marketprodutRoute], { useHash: true })
  ],
  declarations: [
    MarketproductComponent,
    ProductSearchFormComponent
  ],
  providers: [
    ProductService
  ],
})

export class AmazonitemModule {}
