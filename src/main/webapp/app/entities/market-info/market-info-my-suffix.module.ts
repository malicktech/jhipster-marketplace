import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketplacejhipsterSharedModule } from '../../shared';
import {
    MarketInfoMySuffixService,
    MarketInfoMySuffixPopupService,
    MarketInfoMySuffixComponent,
    MarketInfoMySuffixDetailComponent,
    MarketInfoMySuffixDialogComponent,
    MarketInfoMySuffixPopupComponent,
    MarketInfoMySuffixDeletePopupComponent,
    MarketInfoMySuffixDeleteDialogComponent,
    marketInfoRoute,
    marketInfoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketInfoRoute,
    ...marketInfoPopupRoute,
];

@NgModule({
    imports: [
        MarketplacejhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketInfoMySuffixComponent,
        MarketInfoMySuffixDetailComponent,
        MarketInfoMySuffixDialogComponent,
        MarketInfoMySuffixDeleteDialogComponent,
        MarketInfoMySuffixPopupComponent,
        MarketInfoMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        MarketInfoMySuffixComponent,
        MarketInfoMySuffixDialogComponent,
        MarketInfoMySuffixPopupComponent,
        MarketInfoMySuffixDeleteDialogComponent,
        MarketInfoMySuffixDeletePopupComponent,
    ],
    providers: [
        MarketInfoMySuffixService,
        MarketInfoMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterMarketInfoMySuffixModule {}
