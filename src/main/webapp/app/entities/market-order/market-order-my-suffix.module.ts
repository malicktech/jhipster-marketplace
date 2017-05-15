import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketplacejhipsterSharedModule } from '../../shared';
import {
    MarketOrderMySuffixService,
    MarketOrderMySuffixPopupService,
    MarketOrderMySuffixComponent,
    MarketOrderMySuffixDetailComponent,
    MarketOrderMySuffixDialogComponent,
    MarketOrderMySuffixPopupComponent,
    MarketOrderMySuffixDeletePopupComponent,
    MarketOrderMySuffixDeleteDialogComponent,
    marketOrderRoute,
    marketOrderPopupRoute,
    MarketOrderMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...marketOrderRoute,
    ...marketOrderPopupRoute,
];

@NgModule({
    imports: [
        MarketplacejhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketOrderMySuffixComponent,
        MarketOrderMySuffixDetailComponent,
        MarketOrderMySuffixDialogComponent,
        MarketOrderMySuffixDeleteDialogComponent,
        MarketOrderMySuffixPopupComponent,
        MarketOrderMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        MarketOrderMySuffixComponent,
        MarketOrderMySuffixDialogComponent,
        MarketOrderMySuffixPopupComponent,
        MarketOrderMySuffixDeleteDialogComponent,
        MarketOrderMySuffixDeletePopupComponent,
    ],
    providers: [
        MarketOrderMySuffixService,
        MarketOrderMySuffixPopupService,
        MarketOrderMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterMarketOrderMySuffixModule {}
