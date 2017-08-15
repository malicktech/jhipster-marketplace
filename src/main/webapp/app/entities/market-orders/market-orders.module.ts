import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    MarketOrdersService,
    MarketOrdersPopupService,
    MarketOrdersComponent,
    MarketOrdersDetailComponent,
    MarketOrdersDialogComponent,
    MarketOrdersPopupComponent,
    MarketOrdersDeletePopupComponent,
    MarketOrdersDeleteDialogComponent,
    marketOrdersRoute,
    marketOrdersPopupRoute,
    MarketOrdersResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...marketOrdersRoute,
    ...marketOrdersPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketOrdersComponent,
        MarketOrdersDetailComponent,
        MarketOrdersDialogComponent,
        MarketOrdersDeleteDialogComponent,
        MarketOrdersPopupComponent,
        MarketOrdersDeletePopupComponent,
    ],
    entryComponents: [
        MarketOrdersComponent,
        MarketOrdersDialogComponent,
        MarketOrdersPopupComponent,
        MarketOrdersDeleteDialogComponent,
        MarketOrdersDeletePopupComponent,
    ],
    providers: [
        MarketOrdersService,
        MarketOrdersPopupService,
        MarketOrdersResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketMarketOrdersModule {}
