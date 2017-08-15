import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketComponent } from './market.component';
import { MarketDetailComponent } from './market-detail.component';
import { MarketPopupComponent } from './market-dialog.component';
import { MarketDeletePopupComponent } from './market-delete-dialog.component';

export const marketRoute: Routes = [
    {
        path: 'market',
        component: MarketComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.market.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market/:id',
        component: MarketDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.market.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketPopupRoute: Routes = [
    {
        path: 'market-new',
        component: MarketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market/:id/edit',
        component: MarketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market/:id/delete',
        component: MarketDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
