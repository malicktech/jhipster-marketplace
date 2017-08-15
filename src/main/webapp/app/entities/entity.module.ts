import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MarketOperatorModule } from './operator/operator.module';
import { MarketCustomerModule } from './customer/customer.module';
import { MarketCustomerAddressModule } from './customer-address/customer-address.module';
import { MarketRegionModule } from './region/region.module';
import { MarketCountryModule } from './country/country.module';
import { MarketMarketModule } from './market/market.module';
import { MarketProductCategoryModule } from './product-category/product-category.module';
import { MarketMarketInfoModule } from './market-info/market-info.module';
import { MarketMarketOrdersModule } from './market-orders/market-orders.module';
import { MarketShipmentsModule } from './shipments/shipments.module';
import { MarketInvoicesModule } from './invoices/invoices.module';
import { MarketPaymentModule } from './payment/payment.module';
import { MarketMarketOrderlineModule } from './market-orderline/market-orderline.module';
import { MarketMarketOrderItemsDetailsModule } from './market-order-items-details/market-order-items-details.module';
import { MarketMarketProductModule } from './market-product/market-product.module';
import { MarketMarketProductCategoryModule } from './market-product-category/market-product-category.module';
import { MarketMarketProductAttributesModule } from './market-product-attributes/market-product-attributes.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MarketOperatorModule,
        MarketCustomerModule,
        MarketCustomerAddressModule,
        MarketRegionModule,
        MarketCountryModule,
        MarketMarketModule,
        MarketProductCategoryModule,
        MarketMarketInfoModule,
        MarketMarketOrdersModule,
        MarketShipmentsModule,
        MarketInvoicesModule,
        MarketPaymentModule,
        MarketMarketOrderlineModule,
        MarketMarketOrderItemsDetailsModule,
        MarketMarketProductModule,
        MarketMarketProductCategoryModule,
        MarketMarketProductAttributesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MarketEntityModule {}
