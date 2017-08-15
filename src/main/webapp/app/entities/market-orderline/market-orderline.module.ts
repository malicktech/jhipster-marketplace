import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    MarketOrderlineService,
    MarketOrderlinePopupService,
    MarketOrderlineComponent,
    MarketOrderlineDetailComponent,
    MarketOrderlineDialogComponent,
    MarketOrderlinePopupComponent,
    MarketOrderlineDeletePopupComponent,
    MarketOrderlineDeleteDialogComponent,
    marketOrderlineRoute,
    marketOrderlinePopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketOrderlineRoute,
    ...marketOrderlinePopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketOrderlineComponent,
        MarketOrderlineDetailComponent,
        MarketOrderlineDialogComponent,
        MarketOrderlineDeleteDialogComponent,
        MarketOrderlinePopupComponent,
        MarketOrderlineDeletePopupComponent,
    ],
    entryComponents: [
        MarketOrderlineComponent,
        MarketOrderlineDialogComponent,
        MarketOrderlinePopupComponent,
        MarketOrderlineDeleteDialogComponent,
        MarketOrderlineDeletePopupComponent,
    ],
    providers: [
        MarketOrderlineService,
        MarketOrderlinePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketMarketOrderlineModule {}
