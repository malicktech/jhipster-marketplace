import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarketplacejhipsterSharedModule } from '../../shared';
import {
    ProductCategoryMySuffixService,
    ProductCategoryMySuffixPopupService,
    ProductCategoryMySuffixComponent,
    ProductCategoryMySuffixDetailComponent,
    ProductCategoryMySuffixDialogComponent,
    ProductCategoryMySuffixPopupComponent,
    ProductCategoryMySuffixDeletePopupComponent,
    ProductCategoryMySuffixDeleteDialogComponent,
    productCategoryRoute,
    productCategoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...productCategoryRoute,
    ...productCategoryPopupRoute,
];

@NgModule({
    imports: [
        MarketplacejhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductCategoryMySuffixComponent,
        ProductCategoryMySuffixDetailComponent,
        ProductCategoryMySuffixDialogComponent,
        ProductCategoryMySuffixDeleteDialogComponent,
        ProductCategoryMySuffixPopupComponent,
        ProductCategoryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ProductCategoryMySuffixComponent,
        ProductCategoryMySuffixDialogComponent,
        ProductCategoryMySuffixPopupComponent,
        ProductCategoryMySuffixDeleteDialogComponent,
        ProductCategoryMySuffixDeletePopupComponent,
    ],
    providers: [
        ProductCategoryMySuffixService,
        ProductCategoryMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterProductCategoryMySuffixModule {}
