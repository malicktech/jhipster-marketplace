import { Injectable } from '@angular/core'; // imported the Angular Injectable function and applied that function as an @Injectable() decorator.
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Product } from './product-model';

// the service data from anywhere—a web service, local storage, or a mock data source
@Injectable()
export class ProductService {

    private apiUrl = 'http://localhost:8080/api';  // URL to web api

    // private headers;
    private headers = new Headers({ 'Content-Type': 'application/json' });
    // this.headers.append('Access-Control-Allow-Origin', '*');

    constructor(private http: Http) { }

    getProducts(market: string, searchindex: string, query: string, itempage: string): Promise<Product[]> { // Promise-returning
        const path = `${market}/search?query=${query}&searchindex=${searchindex}&itempage=${itempage}`;
        const url = `${this.apiUrl}/${path}`;

        console.log(url);
        return this.http.get(url) // returns an RxJS Observable
            .toPromise() // converted the Observable to a Promise
            .then((response) => response.json() as Product[]) // Extracting the data in the then callback
            .catch(this.handleError); // catch server failures
    }

    getProduct(market: string, asin: String): Promise<Product> {
        const path = `${market}/lookup`;
        const url = `${this.apiUrl}/${path}/${asin}`;
        return this.http.get(url)
            .toPromise()
            .then((response) => response.json() as Product)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
