import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketSharedModule } from '../../shared';
import {
    CustomerAddressService,
    CustomerAddressPopupService,
    CustomerAddressComponent,
    CustomerAddressDetailComponent,
    CustomerAddressDialogComponent,
    CustomerAddressPopupComponent,
    CustomerAddressDeletePopupComponent,
    CustomerAddressDeleteDialogComponent,
    customerAddressRoute,
    customerAddressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...customerAddressRoute,
    ...customerAddressPopupRoute,
];

@NgModule({
    imports: [
        MarketSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerAddressComponent,
        CustomerAddressDetailComponent,
        CustomerAddressDialogComponent,
        CustomerAddressDeleteDialogComponent,
        CustomerAddressPopupComponent,
        CustomerAddressDeletePopupComponent,
    ],
    entryComponents: [
        CustomerAddressComponent,
        CustomerAddressDialogComponent,
        CustomerAddressPopupComponent,
        CustomerAddressDeleteDialogComponent,
        CustomerAddressDeletePopupComponent,
    ],
    providers: [
        CustomerAddressService,
        CustomerAddressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketCustomerAddressModule {}
