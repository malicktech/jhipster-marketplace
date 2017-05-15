import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketplacejhipsterSharedModule } from '../../shared';
import { MarketplacejhipsterAdminModule } from '../../admin/admin.module';
import {
    OperatorMySuffixService,
    OperatorMySuffixPopupService,
    OperatorMySuffixComponent,
    OperatorMySuffixDetailComponent,
    OperatorMySuffixDialogComponent,
    OperatorMySuffixPopupComponent,
    OperatorMySuffixDeletePopupComponent,
    OperatorMySuffixDeleteDialogComponent,
    operatorRoute,
    operatorPopupRoute,
    OperatorMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...operatorRoute,
    ...operatorPopupRoute,
];

@NgModule({
    imports: [
        MarketplacejhipsterSharedModule,
        MarketplacejhipsterAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OperatorMySuffixComponent,
        OperatorMySuffixDetailComponent,
        OperatorMySuffixDialogComponent,
        OperatorMySuffixDeleteDialogComponent,
        OperatorMySuffixPopupComponent,
        OperatorMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        OperatorMySuffixComponent,
        OperatorMySuffixDialogComponent,
        OperatorMySuffixPopupComponent,
        OperatorMySuffixDeleteDialogComponent,
        OperatorMySuffixDeletePopupComponent,
    ],
    providers: [
        OperatorMySuffixService,
        OperatorMySuffixPopupService,
        OperatorMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterOperatorMySuffixModule {}
