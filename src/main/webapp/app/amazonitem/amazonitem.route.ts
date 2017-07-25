import { Routes } from '@angular/router';

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
