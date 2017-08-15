import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarketOrdersComponent } from './market-orders.component';
import { MarketOrdersDetailComponent } from './market-orders-detail.component';
import { MarketOrdersPopupComponent } from './market-orders-dialog.component';
import { MarketOrdersDeletePopupComponent } from './market-orders-delete-dialog.component';

@Injectable()
export class MarketOrdersResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

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

export const marketOrdersRoute: Routes = [
    {
        path: 'market-orders',
        component: MarketOrdersComponent,
        resolve: {
            'pagingParams': MarketOrdersResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrders.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-orders/:id',
        component: MarketOrdersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrders.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketOrdersPopupRoute: Routes = [
    {
        path: 'market-orders-new',
        component: MarketOrdersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrders.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-orders/:id/edit',
        component: MarketOrdersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrders.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-orders/:id/delete',
        component: MarketOrdersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketApp.marketOrders.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
