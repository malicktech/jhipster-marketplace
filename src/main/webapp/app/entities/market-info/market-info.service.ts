import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketInfo } from './market-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketInfoService {

    private resourceUrl = 'api/market-infos';
    private resourceSearchUrl = 'api/_search/market-infos';

    constructor(private http: Http) { }

    create(marketInfo: MarketInfo): Observable<MarketInfo> {
        const copy = this.convert(marketInfo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketInfo: MarketInfo): Observable<MarketInfo> {
        const copy = this.convert(marketInfo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketInfo> {
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

    private convert(marketInfo: MarketInfo): MarketInfo {
        const copy: MarketInfo = Object.assign({}, marketInfo);
        return copy;
    }
}
