import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { MarketSharedModule, UserRouteAccessService } from './shared';
import { MarketHomeModule } from './home/home.module';
import { MarketAdminModule } from './admin/admin.module';
import { MarketAccountModule } from './account/account.module';
import { MarketEntityModule } from './entities/entity.module';
import { AmazonitemModule } from './amazonitem/amazonitem.module';

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
        MarketSharedModule,
        MarketHomeModule,
        MarketAdminModule,
        MarketAccountModule,
        MarketEntityModule,
        AmazonitemModule
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
export class MarketAppModule {}
