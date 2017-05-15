import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketInfoMySuffix } from './market-info-my-suffix.model';

@Injectable()
export class MarketInfoMySuffixService {

    private resourceUrl = 'api/market-infos';
    private resourceSearchUrl = 'api/_search/market-infos';

    constructor(private http: Http) { }

    create(marketInfo: MarketInfoMySuffix): Observable<MarketInfoMySuffix> {
        const copy = this.convert(marketInfo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketInfo: MarketInfoMySuffix): Observable<MarketInfoMySuffix> {
        const copy = this.convert(marketInfo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketInfoMySuffix> {
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

    private convert(marketInfo: MarketInfoMySuffix): MarketInfoMySuffix {
        const copy: MarketInfoMySuffix = Object.assign({}, marketInfo);
        return copy;
    }
}
