import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketplacejhipsterSharedModule } from '../../shared';
import {
    CustomerAddressMySuffixService,
    CustomerAddressMySuffixPopupService,
    CustomerAddressMySuffixComponent,
    CustomerAddressMySuffixDetailComponent,
    CustomerAddressMySuffixDialogComponent,
    CustomerAddressMySuffixPopupComponent,
    CustomerAddressMySuffixDeletePopupComponent,
    CustomerAddressMySuffixDeleteDialogComponent,
    customerAddressRoute,
    customerAddressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...customerAddressRoute,
    ...customerAddressPopupRoute,
];

@NgModule({
    imports: [
        MarketplacejhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerAddressMySuffixComponent,
        CustomerAddressMySuffixDetailComponent,
        CustomerAddressMySuffixDialogComponent,
        CustomerAddressMySuffixDeleteDialogComponent,
        CustomerAddressMySuffixPopupComponent,
        CustomerAddressMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CustomerAddressMySuffixComponent,
        CustomerAddressMySuffixDialogComponent,
        CustomerAddressMySuffixPopupComponent,
        CustomerAddressMySuffixDeleteDialogComponent,
        CustomerAddressMySuffixDeletePopupComponent,
    ],
    providers: [
        CustomerAddressMySuffixService,
        CustomerAddressMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterCustomerAddressMySuffixModule {}
