import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketOrderMySuffixComponent } from './market-order-my-suffix.component';
import { MarketOrderMySuffixDetailComponent } from './market-order-my-suffix-detail.component';
import { MarketOrderMySuffixPopupComponent } from './market-order-my-suffix-dialog.component';
import { MarketOrderMySuffixDeletePopupComponent } from './market-order-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MarketOrderMySuffixResolvePagingParams implements Resolve<any> {

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

export const marketOrderRoute: Routes = [
    {
        path: 'market-order-my-suffix',
        component: MarketOrderMySuffixComponent,
        resolve: {
            'pagingParams': MarketOrderMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-order-my-suffix/:id',
        component: MarketOrderMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketOrderPopupRoute: Routes = [
    {
        path: 'market-order-my-suffix-new',
        component: MarketOrderMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-order-my-suffix/:id/edit',
        component: MarketOrderMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-order-my-suffix/:id/delete',
        component: MarketOrderMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.marketOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
