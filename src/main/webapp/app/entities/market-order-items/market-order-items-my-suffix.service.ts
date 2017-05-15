import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketOrderItemsMySuffix } from './market-order-items-my-suffix.model';

@Injectable()
export class MarketOrderItemsMySuffixService {

    private resourceUrl = 'api/market-order-items';
    private resourceSearchUrl = 'api/_search/market-order-items';

    constructor(private http: Http) { }

    create(marketOrderItems: MarketOrderItemsMySuffix): Observable<MarketOrderItemsMySuffix> {
        const copy = this.convert(marketOrderItems);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketOrderItems: MarketOrderItemsMySuffix): Observable<MarketOrderItemsMySuffix> {
        const copy = this.convert(marketOrderItems);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketOrderItemsMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
        ;
    }
    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    private convert(marketOrderItems: MarketOrderItemsMySuffix): MarketOrderItemsMySuffix {
        const copy: MarketOrderItemsMySuffix = Object.assign({}, marketOrderItems);
        return copy;
    }
}
