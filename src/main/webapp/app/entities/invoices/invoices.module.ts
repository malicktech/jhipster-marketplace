import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    InvoicesService,
    InvoicesPopupService,
    InvoicesComponent,
    InvoicesDetailComponent,
    InvoicesDialogComponent,
    InvoicesPopupComponent,
    InvoicesDeletePopupComponent,
    InvoicesDeleteDialogComponent,
    invoicesRoute,
    invoicesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...invoicesRoute,
    ...invoicesPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InvoicesComponent,
        InvoicesDetailComponent,
        InvoicesDialogComponent,
        InvoicesDeleteDialogComponent,
        InvoicesPopupComponent,
        InvoicesDeletePopupComponent,
    ],
    entryComponents: [
        InvoicesComponent,
        InvoicesDialogComponent,
        InvoicesPopupComponent,
        InvoicesDeleteDialogComponent,
        InvoicesDeletePopupComponent,
    ],
    providers: [
        InvoicesService,
        InvoicesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketInvoicesModule {}
