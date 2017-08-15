import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketProductComponent } from './market-product.component';
import { MarketProductDetailComponent } from './market-product-detail.component';
import { MarketProductPopupComponent } from './market-product-dialog.component';
import { MarketProductDeletePopupComponent } from './market-product-delete-dialog.component';

export const marketProductRoute: Routes = [
    {
        path: 'market-product',
        component: MarketProductComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-product/:id',
        component: MarketProductDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketProductPopupRoute: Routes = [
    {
        path: 'market-product-new',
        component: MarketProductPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-product/:id/edit',
        component: MarketProductPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-product/:id/delete',
        component: MarketProductDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
