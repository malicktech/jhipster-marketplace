import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CustomerAddressComponent } from './customer-address.component';
import { CustomerAddressDetailComponent } from './customer-address-detail.component';
import { CustomerAddressPopupComponent } from './customer-address-dialog.component';
import { CustomerAddressDeletePopupComponent } from './customer-address-delete-dialog.component';

export const customerAddressRoute: Routes = [
    {
        path: 'customer-address',
        component: CustomerAddressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customer-address/:id',
        component: CustomerAddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerAddressPopupRoute: Routes = [
    {
        path: 'customer-address-new',
        component: CustomerAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-address/:id/edit',
        component: CustomerAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-address/:id/delete',
        component: CustomerAddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.customerAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
