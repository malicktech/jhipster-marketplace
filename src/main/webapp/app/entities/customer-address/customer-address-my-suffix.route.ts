import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerAddressMySuffixComponent } from './customer-address-my-suffix.component';
import { CustomerAddressMySuffixDetailComponent } from './customer-address-my-suffix-detail.component';
import { CustomerAddressMySuffixPopupComponent } from './customer-address-my-suffix-dialog.component';
import { CustomerAddressMySuffixDeletePopupComponent } from './customer-address-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const customerAddressRoute: Routes = [
    {
        path: 'customer-address-my-suffix',
        component: CustomerAddressMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customer-address-my-suffix/:id',
        component: CustomerAddressMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerAddressPopupRoute: Routes = [
    {
        path: 'customer-address-my-suffix-new',
        component: CustomerAddressMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-address-my-suffix/:id/edit',
        component: CustomerAddressMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-address-my-suffix/:id/delete',
        component: CustomerAddressMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
