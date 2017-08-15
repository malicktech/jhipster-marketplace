import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketProductAttributesComponent } from './market-product-attributes.component';
import { MarketProductAttributesDetailComponent } from './market-product-attributes-detail.component';
import { MarketProductAttributesPopupComponent } from './market-product-attributes-dialog.component';
import { MarketProductAttributesDeletePopupComponent } from './market-product-attributes-delete-dialog.component';

export const marketProductAttributesRoute: Routes = [
    {
        path: 'market-product-attributes',
        component: MarketProductAttributesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-product-attributes/:id',
        component: MarketProductAttributesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketProductAttributesPopupRoute: Routes = [
    {
        path: 'market-product-attributes-new',
        component: MarketProductAttributesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductAttributes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-product-attributes/:id/edit',
        component: MarketProductAttributesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductAttributes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-product-attributes/:id/delete',
        component: MarketProductAttributesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketProductAttributes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
