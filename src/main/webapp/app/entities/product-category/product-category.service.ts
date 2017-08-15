import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ProductCategory } from './product-category.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProductCategoryService {

    private resourceUrl = 'api/product-categories';
    private resourceSearchUrl = 'api/_search/product-categories';

    constructor(private http: Http) { }

    create(productCategory: ProductCategory): Observable<ProductCategory> {
        const copy = this.convert(productCategory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(productCategory: ProductCategory): Observable<ProductCategory> {
        const copy = this.convert(productCategory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProductCategory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(productCategory: ProductCategory): ProductCategory {
        const copy: ProductCategory = Object.assign({}, productCategory);
        return copy;
    }
}
