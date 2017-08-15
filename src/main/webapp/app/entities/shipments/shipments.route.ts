import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ShipmentsComponent } from './shipments.component';
import { ShipmentsDetailComponent } from './shipments-detail.component';
import { ShipmentsPopupComponent } from './shipments-dialog.component';
import { ShipmentsDeletePopupComponent } from './shipments-delete-dialog.component';

export const shipmentsRoute: Routes = [
    {
        path: 'shipments',
        component: ShipmentsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.shipments.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shipments/:id',
        component: ShipmentsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.shipments.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shipmentsPopupRoute: Routes = [
    {
        path: 'shipments-new',
        component: ShipmentsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.shipments.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipments/:id/edit',
        component: ShipmentsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.shipments.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipments/:id/delete',
        component: ShipmentsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.shipments.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
