import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketProductCategoryComponent } from './market-product-category.component';
import { MarketProductCategoryDetailComponent } from './market-product-category-detail.component';
import { MarketProductCategoryPopupComponent } from './market-product-category-dialog.component';
import { MarketProductCategoryDeletePopupComponent } from './market-product-category-delete-dialog.component';

export const marketProductCategoryRoute: Routes = [
    {
        path: 'market-product-category',
        component: MarketProductCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-product-category/:id',
        component: MarketProductCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketProductCategoryPopupRoute: Routes = [
    {
        path: 'market-product-category-new',
        component: MarketProductCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-product-category/:id/edit',
        component: MarketProductCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-product-category/:id/delete',
        component: MarketProductCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
