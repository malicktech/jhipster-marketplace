import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketProductAttributes } from './market-product-attributes.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketProductAttributesService {

    private resourceUrl = 'api/market-product-attributes';
    private resourceSearchUrl = 'api/_search/market-product-attributes';

    constructor(private http: Http) { }

    create(marketProductAttributes: MarketProductAttributes): Observable<MarketProductAttributes> {
        const copy = this.convert(marketProductAttributes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketProductAttributes: MarketProductAttributes): Observable<MarketProductAttributes> {
        const copy = this.convert(marketProductAttributes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketProductAttributes> {
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

    private convert(marketProductAttributes: MarketProductAttributes): MarketProductAttributes {
        const copy: MarketProductAttributes = Object.assign({}, marketProductAttributes);
        return copy;
    }
}
