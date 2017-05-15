import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OperatorMySuffixComponent } from './operator-my-suffix.component';
import { OperatorMySuffixDetailComponent } from './operator-my-suffix-detail.component';
import { OperatorMySuffixPopupComponent } from './operator-my-suffix-dialog.component';
import { OperatorMySuffixDeletePopupComponent } from './operator-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OperatorMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const operatorRoute: Routes = [
    {
        path: 'operator-my-suffix',
        component: OperatorMySuffixComponent,
        resolve: {
            'pagingParams': OperatorMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.operator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'operator-my-suffix/:id',
        component: OperatorMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.operator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operatorPopupRoute: Routes = [
    {
        path: 'operator-my-suffix-new',
        component: OperatorMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.operator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operator-my-suffix/:id/edit',
        component: OperatorMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.operator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operator-my-suffix/:id/delete',
        component: OperatorMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.operator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
