import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InvoicesComponent } from './invoices.component';
import { InvoicesDetailComponent } from './invoices-detail.component';
import { InvoicesPopupComponent } from './invoices-dialog.component';
import { InvoicesDeletePopupComponent } from './invoices-delete-dialog.component';

export const invoicesRoute: Routes = [
    {
        path: 'invoices',
        component: InvoicesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'invoices/:id',
        component: InvoicesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invoicesPopupRoute: Routes = [
    {
        path: 'invoices-new',
        component: InvoicesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invoices/:id/edit',
        component: InvoicesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invoices/:id/delete',
        component: InvoicesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
