import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    MarketProductAttributesService,
    MarketProductAttributesPopupService,
    MarketProductAttributesComponent,
    MarketProductAttributesDetailComponent,
    MarketProductAttributesDialogComponent,
    MarketProductAttributesPopupComponent,
    MarketProductAttributesDeletePopupComponent,
    MarketProductAttributesDeleteDialogComponent,
    marketProductAttributesRoute,
    marketProductAttributesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketProductAttributesRoute,
    ...marketProductAttributesPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketProductAttributesComponent,
        MarketProductAttributesDetailComponent,
        MarketProductAttributesDialogComponent,
        MarketProductAttributesDeleteDialogComponent,
        MarketProductAttributesPopupComponent,
        MarketProductAttributesDeletePopupComponent,
    ],
    entryComponents: [
        MarketProductAttributesComponent,
        MarketProductAttributesDialogComponent,
        MarketProductAttributesPopupComponent,
        MarketProductAttributesDeleteDialogComponent,
        MarketProductAttributesDeletePopupComponent,
    ],
    providers: [
        MarketProductAttributesService,
        MarketProductAttributesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketMarketProductAttributesModule {}
