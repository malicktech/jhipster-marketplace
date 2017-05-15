import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { OperatorMySuffix } from './operator-my-suffix.model';
import { DateUtils } from 'ng-jhipster';

@Injectable()
export class OperatorMySuffixService {

    private resourceUrl = 'api/operators';
    private resourceSearchUrl = 'api/_search/operators';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(operator: OperatorMySuffix): Observable<OperatorMySuffix> {
        const copy = this.convert(operator);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(operator: OperatorMySuffix): Observable<OperatorMySuffix> {
        const copy = this.convert(operator);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<OperatorMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.hireDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.hireDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    private convertResponse(res: Response): Response {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].hireDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].hireDate);
        }
        res.json().data = jsonResponse;
        return res;
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

    private convert(operator: OperatorMySuffix): OperatorMySuffix {
        const copy: OperatorMySuffix = Object.assign({}, operator);

        copy.hireDate = this.dateUtils.toDate(operator.hireDate);
        return copy;
    }
}
