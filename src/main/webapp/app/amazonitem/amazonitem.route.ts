import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { CartComponent } from './cart/cart.component';

import {
    marketprodutRoute,
    productDetailsRoute,
    cartRoute
} from './';

const AMAZON_ROUTES = [
    marketprodutRoute,
    productDetailsRoute,
    //    cartRoute
    {
        path: 'cart/:operation/:cartId',
        component: CartComponent,
        data: {
            authorities: [],
            pageTitle: 'home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amazonitemState: Routes = [{
    path: '',
    children: AMAZON_ROUTES
}];
