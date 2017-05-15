import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketOrderItemsMySuffixComponent } from './market-order-items-my-suffix.component';
import { MarketOrderItemsMySuffixDetailComponent } from './market-order-items-my-suffix-detail.component';
import { MarketOrderItemsMySuffixPopupComponent } from './market-order-items-my-suffix-dialog.component';
import { MarketOrderItemsMySuffixDeletePopupComponent } from './market-order-items-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const marketOrderItemsRoute: Routes = [
    {
        path: 'market-order-items-my-suffix',
        component: MarketOrderItemsMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrderItems.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-order-items-my-suffix/:id',
        component: MarketOrderItemsMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrderItems.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketOrderItemsPopupRoute: Routes = [
    {
        path: 'market-order-items-my-suffix-new',
        component: MarketOrderItemsMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrderItems.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-order-items-my-suffix/:id/edit',
        component: MarketOrderItemsMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrderItems.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-order-items-my-suffix/:id/delete',
        component: MarketOrderItemsMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrderItems.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
