import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    ShipmentsService,
    ShipmentsPopupService,
    ShipmentsComponent,
    ShipmentsDetailComponent,
    ShipmentsDialogComponent,
    ShipmentsPopupComponent,
    ShipmentsDeletePopupComponent,
    ShipmentsDeleteDialogComponent,
    shipmentsRoute,
    shipmentsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...shipmentsRoute,
    ...shipmentsPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShipmentsComponent,
        ShipmentsDetailComponent,
        ShipmentsDialogComponent,
        ShipmentsDeleteDialogComponent,
        ShipmentsPopupComponent,
        ShipmentsDeletePopupComponent,
    ],
    entryComponents: [
        ShipmentsComponent,
        ShipmentsDialogComponent,
        ShipmentsPopupComponent,
        ShipmentsDeleteDialogComponent,
        ShipmentsDeletePopupComponent,
    ],
    providers: [
        ShipmentsService,
        ShipmentsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketShipmentsModule {}
