import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketplacejhipsterSharedModule } from '../../shared';
import {
    MarketOrderItemsMySuffixService,
    MarketOrderItemsMySuffixPopupService,
    MarketOrderItemsMySuffixComponent,
    MarketOrderItemsMySuffixDetailComponent,
    MarketOrderItemsMySuffixDialogComponent,
    MarketOrderItemsMySuffixPopupComponent,
    MarketOrderItemsMySuffixDeletePopupComponent,
    MarketOrderItemsMySuffixDeleteDialogComponent,
    marketOrderItemsRoute,
    marketOrderItemsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketOrderItemsRoute,
    ...marketOrderItemsPopupRoute,
];

@NgModule({
    imports: [
        MarketplacejhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketOrderItemsMySuffixComponent,
        MarketOrderItemsMySuffixDetailComponent,
        MarketOrderItemsMySuffixDialogComponent,
        MarketOrderItemsMySuffixDeleteDialogComponent,
        MarketOrderItemsMySuffixPopupComponent,
        MarketOrderItemsMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        MarketOrderItemsMySuffixComponent,
        MarketOrderItemsMySuffixDialogComponent,
        MarketOrderItemsMySuffixPopupComponent,
        MarketOrderItemsMySuffixDeleteDialogComponent,
        MarketOrderItemsMySuffixDeletePopupComponent,
    ],
    providers: [
        MarketOrderItemsMySuffixService,
        MarketOrderItemsMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterMarketOrderItemsMySuffixModule {}
