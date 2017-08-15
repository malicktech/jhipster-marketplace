import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    MarketProductService,
    MarketProductPopupService,
    MarketProductComponent,
    MarketProductDetailComponent,
    MarketProductDialogComponent,
    MarketProductPopupComponent,
    MarketProductDeletePopupComponent,
    MarketProductDeleteDialogComponent,
    marketProductRoute,
    marketProductPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketProductRoute,
    ...marketProductPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketProductComponent,
        MarketProductDetailComponent,
        MarketProductDialogComponent,
        MarketProductDeleteDialogComponent,
        MarketProductPopupComponent,
        MarketProductDeletePopupComponent,
    ],
    entryComponents: [
        MarketProductComponent,
        MarketProductDialogComponent,
        MarketProductPopupComponent,
        MarketProductDeleteDialogComponent,
        MarketProductDeletePopupComponent,
    ],
    providers: [
        MarketProductService,
        MarketProductPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketMarketProductModule {}
