import { Routes } from '@angular/router';

import {
    marketprodutRoute,
    ProductDetailsRoute
} from './';

const AMAZON_ROUTES = [
   marketprodutRoute,
   ProductDetailsRoute
];

export const amazonitemState: Routes = [{
    path: '',
    children: AMAZON_ROUTES
}];
