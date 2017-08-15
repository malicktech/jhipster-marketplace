import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketInfoComponent } from './market-info.component';
import { MarketInfoDetailComponent } from './market-info-detail.component';
import { MarketInfoPopupComponent } from './market-info-dialog.component';
import { MarketInfoDeletePopupComponent } from './market-info-delete-dialog.component';

export const marketInfoRoute: Routes = [
    {
        path: 'market-info',
        component: MarketInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-info/:id',
        component: MarketInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketInfoPopupRoute: Routes = [
    {
        path: 'market-info-new',
        component: MarketInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-info/:id/edit',
        component: MarketInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-info/:id/delete',
        component: MarketInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
