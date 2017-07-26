import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CartComponent } from './cart.component';

export const cartRoute: Route = {
    path: 'cart/:operation/:id',
    component: CartComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    },
    canActivate: [UserRouteAccessService]
};
