import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketOrderlineComponent } from './market-orderline.component';
import { MarketOrderlineDetailComponent } from './market-orderline-detail.component';
import { MarketOrderlinePopupComponent } from './market-orderline-dialog.component';
import { MarketOrderlineDeletePopupComponent } from './market-orderline-delete-dialog.component';

export const marketOrderlineRoute: Routes = [
    {
        path: 'market-orderline',
        component: MarketOrderlineComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderline.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-orderline/:id',
        component: MarketOrderlineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderline.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketOrderlinePopupRoute: Routes = [
    {
        path: 'market-orderline-new',
        component: MarketOrderlinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-orderline/:id/edit',
        component: MarketOrderlinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-orderline/:id/delete',
        component: MarketOrderlineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrderline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
