import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketInfoMySuffixComponent } from './market-info-my-suffix.component';
import { MarketInfoMySuffixDetailComponent } from './market-info-my-suffix-detail.component';
import { MarketInfoMySuffixPopupComponent } from './market-info-my-suffix-dialog.component';
import { MarketInfoMySuffixDeletePopupComponent } from './market-info-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const marketInfoRoute: Routes = [
    {
        path: 'market-info-my-suffix',
        component: MarketInfoMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-info-my-suffix/:id',
        component: MarketInfoMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketInfoPopupRoute: Routes = [
    {
        path: 'market-info-my-suffix-new',
        component: MarketInfoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-info-my-suffix/:id/edit',
        component: MarketInfoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-info-my-suffix/:id/delete',
        component: MarketInfoMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
