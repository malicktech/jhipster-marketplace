import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketplacejhipsterSharedModule } from '../../shared';
import {
    MarketMySuffixService,
    MarketMySuffixPopupService,
    MarketMySuffixComponent,
    MarketMySuffixDetailComponent,
    MarketMySuffixDialogComponent,
    MarketMySuffixPopupComponent,
    MarketMySuffixDeletePopupComponent,
    MarketMySuffixDeleteDialogComponent,
    marketRoute,
    marketPopupRoute,
    MarketMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...marketRoute,
    ...marketPopupRoute,
];

@NgModule({
    imports: [
        MarketplacejhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketMySuffixComponent,
        MarketMySuffixDetailComponent,
        MarketMySuffixDialogComponent,
        MarketMySuffixDeleteDialogComponent,
        MarketMySuffixPopupComponent,
        MarketMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        MarketMySuffixComponent,
        MarketMySuffixDialogComponent,
        MarketMySuffixPopupComponent,
        MarketMySuffixDeleteDialogComponent,
        MarketMySuffixDeletePopupComponent,
    ],
    providers: [
        MarketMySuffixService,
        MarketMySuffixPopupService,
        MarketMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterMarketMySuffixModule {}
