import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';

import {
    marketprodutRoute,
    productDetailsRoute
} from './';

const AMAZON_ROUTES = [
    marketprodutRoute,
    productDetailsRoute
];

export const amazonitemState: Routes = [{
    path: '',
    children: AMAZON_ROUTES
}];
