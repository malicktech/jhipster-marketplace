import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    MarketOrderItemsDetailsService,
    MarketOrderItemsDetailsPopupService,
    MarketOrderItemsDetailsComponent,
    MarketOrderItemsDetailsDetailComponent,
    MarketOrderItemsDetailsDialogComponent,
    MarketOrderItemsDetailsPopupComponent,
    MarketOrderItemsDetailsDeletePopupComponent,
    MarketOrderItemsDetailsDeleteDialogComponent,
    marketOrderItemsDetailsRoute,
    marketOrderItemsDetailsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketOrderItemsDetailsRoute,
    ...marketOrderItemsDetailsPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketOrderItemsDetailsComponent,
        MarketOrderItemsDetailsDetailComponent,
        MarketOrderItemsDetailsDialogComponent,
        MarketOrderItemsDetailsDeleteDialogComponent,
        MarketOrderItemsDetailsPopupComponent,
        MarketOrderItemsDetailsDeletePopupComponent,
    ],
    entryComponents: [
        MarketOrderItemsDetailsComponent,
        MarketOrderItemsDetailsDialogComponent,
        MarketOrderItemsDetailsPopupComponent,
        MarketOrderItemsDetailsDeleteDialogComponent,
        MarketOrderItemsDetailsDeletePopupComponent,
    ],
    providers: [
        MarketOrderItemsDetailsService,
        MarketOrderItemsDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketMarketOrderItemsDetailsModule {}
