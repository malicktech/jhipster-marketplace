import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MarketproductComponent } from './marketproduct.component';

export const marketprodutRoute: Route = {
    path: 'marketproduct',
    component: MarketproductComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    },
    canActivate: [UserRouteAccessService]
};
