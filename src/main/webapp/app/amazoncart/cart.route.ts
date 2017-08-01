import { Route } from '@angular/router';

import { CartComponent } from './cart.component';

export const CART_ROUTE: Route = {
    path: 'cart',
    component: CartComponent,
    data: {
        authorities: [],
        // pageTitle: 'home.title'
    }
};


