import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarketOrders } from './market-orders.model';
import { MarketOrdersPopupService } from './market-orders-popup.service';
import { MarketOrdersService } from './market-orders.service';
import { Payment, PaymentService } from '../payment';
import { Shipments, ShipmentsService } from '../shipments';
import { Customer, CustomerService } from '../customer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-market-orders-dialog',
    templateUrl: './market-orders-dialog.component.html'
})
export class MarketOrdersDialogComponent implements OnInit {

    marketOrders: MarketOrders;
    isSaving: boolean;

    payments: Payment[];

    shippers: Shipments[];

    customers: Customer[];
    shipdateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private marketOrdersService: MarketOrdersService,
        private paymentService: PaymentService,
        private shipmentsService: ShipmentsService,
        private customerService: CustomerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.paymentService
            .query({filter: 'order-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.marketOrders.paymentId) {
                    this.payments = res.json;
                } else {
                    this.paymentService
                        .find(this.marketOrders.paymentId)
                        .subscribe((subRes: Payment) => {
                            this.payments = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.shipmentsService
            .query({filter: 'order-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.marketOrders.shipperId) {
                    this.shippers = res.json;
                } else {
                    this.shipmentsService
                        .find(this.marketOrders.shipperId)
                        .subscribe((subRes: Shipments) => {
                            this.shippers = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.customerService.query()
            .subscribe((res: ResponseWrapper) => { this.customers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketOrders.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketOrdersService.update(this.marketOrders));
        } else {
            this.subscribeToSaveResponse(
                this.marketOrdersService.create(this.marketOrders));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketOrders>) {
        result.subscribe((res: MarketOrders) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketOrders) {
        this.eventManager.broadcast({ name: 'marketOrdersListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPaymentById(index: number, item: Payment) {
        return item.id;
    }

    trackShipmentsById(index: number, item: Shipments) {
        return item.id;
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-orders-popup',
    template: ''
})
export class MarketOrdersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrdersPopupService: MarketOrdersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketOrdersPopupService
                    .open(MarketOrdersDialogComponent as Component, params['id']);
            } else {
                this.marketOrdersPopupService
                    .open(MarketOrdersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
