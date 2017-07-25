import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProductDetailsComponent } from './product-details.component';

export const productDetailsRoute: Route = {
    path: 'product/:asin',
    component: ProductDetailsComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    },
    canActivate: [UserRouteAccessService]
};
