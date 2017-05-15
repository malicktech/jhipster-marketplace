import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PaymentMySuffixComponent } from './payment-my-suffix.component';
import { PaymentMySuffixDetailComponent } from './payment-my-suffix-detail.component';
import { PaymentMySuffixPopupComponent } from './payment-my-suffix-dialog.component';
import { PaymentMySuffixDeletePopupComponent } from './payment-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const paymentRoute: Routes = [
    {
        path: 'payment-my-suffix',
        component: PaymentMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.payment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payment-my-suffix/:id',
        component: PaymentMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.payment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentPopupRoute: Routes = [
    {
        path: 'payment-my-suffix-new',
        component: PaymentMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.payment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment-my-suffix/:id/edit',
        component: PaymentMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.payment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment-my-suffix/:id/delete',
        component: PaymentMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.payment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
