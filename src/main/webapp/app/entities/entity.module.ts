import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MarketplacejhipsterOperatorMySuffixModule } from './operator/operator-my-suffix.module';
import { MarketplacejhipsterCustomerMySuffixModule } from './customer/customer-my-suffix.module';
import { MarketplacejhipsterCustomerAddressMySuffixModule } from './customer-address/customer-address-my-suffix.module';
import { MarketplacejhipsterRegionMySuffixModule } from './region/region-my-suffix.module';
import { MarketplacejhipsterCountryMySuffixModule } from './country/country-my-suffix.module';
import { MarketplacejhipsterMarketMySuffixModule } from './market/market-my-suffix.module';
import { MarketplacejhipsterProductCategoryMySuffixModule } from './product-category/product-category-my-suffix.module';
import { MarketplacejhipsterMarketInfoMySuffixModule } from './market-info/market-info-my-suffix.module';
import { MarketplacejhipsterMarketOrderMySuffixModule } from './market-order/market-order-my-suffix.module';
import { MarketplacejhipsterPaymentMySuffixModule } from './payment/payment-my-suffix.module';
import { MarketplacejhipsterMarketOrderItemsMySuffixModule } from './market-order-items/market-order-items-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MarketplacejhipsterOperatorMySuffixModule,
        MarketplacejhipsterCustomerMySuffixModule,
        MarketplacejhipsterCustomerAddressMySuffixModule,
        MarketplacejhipsterRegionMySuffixModule,
        MarketplacejhipsterCountryMySuffixModule,
        MarketplacejhipsterMarketMySuffixModule,
        MarketplacejhipsterProductCategoryMySuffixModule,
        MarketplacejhipsterMarketInfoMySuffixModule,
        MarketplacejhipsterMarketOrderMySuffixModule,
        MarketplacejhipsterPaymentMySuffixModule,
        MarketplacejhipsterMarketOrderItemsMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketplacejhipsterEntityModule {}
