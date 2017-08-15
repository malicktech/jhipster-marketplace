import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { MarketOrders } from './market-orders.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketOrdersService {

    private resourceUrl = 'api/market-orders';
    private resourceSearchUrl = 'api/_search/market-orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(marketOrders: MarketOrders): Observable<MarketOrders> {
        const copy = this.convert(marketOrders);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(marketOrders: MarketOrders): Observable<MarketOrders> {
        const copy = this.convert(marketOrders);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<MarketOrders> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.orderDate = this.dateUtils
            .convertDateTimeFromServer(entity.orderDate);
        entity.shipdate = this.dateUtils
            .convertLocalDateFromServer(entity.shipdate);
    }

    private convert(marketOrders: MarketOrders): MarketOrders {
        const copy: MarketOrders = Object.assign({}, marketOrders);

        copy.orderDate = this.dateUtils.toDate(marketOrders.orderDate);
        copy.shipdate = this.dateUtils
            .convertLocalDateToServer(marketOrders.shipdate);
        return copy;
    }
}
