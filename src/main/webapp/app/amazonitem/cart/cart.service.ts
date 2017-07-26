import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Cart } from './cart.model';

@Injectable()
export class CartService {

    private apiUrl = 'http://localhost:8080/api';

    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    getCart(market: string, operation: String, cartId: String, hmac: String, ): Promise<Cart> {
        const path = `${market}/cart/${operation}/${cartId}?hmac=${hmac}`;
        console.log(path);
        const url = `${this.apiUrl}/${path}`;

        return this.http.get(url)
            .toPromise()
            .then((response) => response.json() as Cart)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}
