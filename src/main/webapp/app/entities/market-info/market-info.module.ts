import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    MarketInfoService,
    MarketInfoPopupService,
    MarketInfoComponent,
    MarketInfoDetailComponent,
    MarketInfoDialogComponent,
    MarketInfoPopupComponent,
    MarketInfoDeletePopupComponent,
    MarketInfoDeleteDialogComponent,
    marketInfoRoute,
    marketInfoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketInfoRoute,
    ...marketInfoPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketInfoComponent,
        MarketInfoDetailComponent,
        MarketInfoDialogComponent,
        MarketInfoDeleteDialogComponent,
        MarketInfoPopupComponent,
        MarketInfoDeletePopupComponent,
    ],
    entryComponents: [
        MarketInfoComponent,
        MarketInfoDialogComponent,
        MarketInfoPopupComponent,
        MarketInfoDeleteDialogComponent,
        MarketInfoDeletePopupComponent,
    ],
    providers: [
        MarketInfoService,
        MarketInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketMarketInfoModule {}
