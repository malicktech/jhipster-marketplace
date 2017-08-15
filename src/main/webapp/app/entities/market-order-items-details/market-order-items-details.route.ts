import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketOrderItemsDetailsComponent } from './market-order-items-details.component';
import { MarketOrderItemsDetailsDetailComponent } from './market-order-items-details-detail.component';
import { MarketOrderItemsDetailsPopupComponent } from './market-order-items-details-dialog.component';
import { MarketOrderItemsDetailsDeletePopupComponent } from './market-order-items-details-delete-dialog.component';

export const marketOrderItemsDetailsRoute: Routes = [
    {
        path: 'market-order-items-details',
        component: MarketOrderItemsDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderItemsDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-order-items-details/:id',
        component: MarketOrderItemsDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderItemsDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketOrderItemsDetailsPopupRoute: Routes = [
    {
        path: 'market-order-items-details-new',
        component: MarketOrderItemsDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderItemsDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-order-items-details/:id/edit',
        component: MarketOrderItemsDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderItemsDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-order-items-details/:id/delete',
        component: MarketOrderItemsDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderItemsDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
