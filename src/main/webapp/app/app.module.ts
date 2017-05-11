import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { MarketplacejhipsterSharedModule, UserRouteAccessService } from './shared';
import { MarketplacejhipsterHomeModule } from './home/home.module';
import { MarketplacejhipsterAdminModule } from './admin/admin.module';
import { MarketplacejhipsterAccountModule } from './account/account.module';
import { MarketplacejhipsterEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        MarketplacejhipsterSharedModule,
        MarketplacejhipsterHomeModule,
        MarketplacejhipsterAdminModule,
        MarketplacejhipsterAccountModule,
        MarketplacejhipsterEntityModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class MarketplacejhipsterAppModule {}
