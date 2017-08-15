import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    MarketProductCategoryService,
    MarketProductCategoryPopupService,
    MarketProductCategoryComponent,
    MarketProductCategoryDetailComponent,
    MarketProductCategoryDialogComponent,
    MarketProductCategoryPopupComponent,
    MarketProductCategoryDeletePopupComponent,
    MarketProductCategoryDeleteDialogComponent,
    marketProductCategoryRoute,
    marketProductCategoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketProductCategoryRoute,
    ...marketProductCategoryPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketProductCategoryComponent,
        MarketProductCategoryDetailComponent,
        MarketProductCategoryDialogComponent,
        MarketProductCategoryDeleteDialogComponent,
        MarketProductCategoryPopupComponent,
        MarketProductCategoryDeletePopupComponent,
    ],
    entryComponents: [
        MarketProductCategoryComponent,
        MarketProductCategoryDialogComponent,
        MarketProductCategoryPopupComponent,
        MarketProductCategoryDeleteDialogComponent,
        MarketProductCategoryDeletePopupComponent,
    ],
    providers: [
        MarketProductCategoryService,
        MarketProductCategoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketMarketProductCategoryModule {}
