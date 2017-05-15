import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketMySuffixComponent } from './market-my-suffix.component';
import { MarketMySuffixDetailComponent } from './market-my-suffix-detail.component';
import { MarketMySuffixPopupComponent } from './market-my-suffix-dialog.component';
import { MarketMySuffixDeletePopupComponent } from './market-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MarketMySuffixResolvePagingParams implements Resolve<any> {

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

export const marketRoute: Routes = [
    {
        path: 'market-my-suffix',
        component: MarketMySuffixComponent,
        resolve: {
            'pagingParams': MarketMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.market.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-my-suffix/:id',
        component: MarketMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.market.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketPopupRoute: Routes = [
    {
        path: 'market-my-suffix-new',
        component: MarketMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-my-suffix/:id/edit',
        component: MarketMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-my-suffix/:id/delete',
        component: MarketMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'marketplacejhipsterApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
