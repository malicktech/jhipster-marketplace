import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MarketOrderMySuffix } from './market-order-my-suffix.model';
import { MarketOrderMySuffixPopupService } from './market-order-my-suffix-popup.service';
import { MarketOrderMySuffixService } from './market-order-my-suffix.service';
import { PaymentMySuffix, PaymentMySuffixService } from '../payment';
import { CustomerMySuffix, CustomerMySuffixService } from '../customer';

@Component({
    selector: 'jhi-market-order-my-suffix-dialog',
    templateUrl: './market-order-my-suffix-dialog.component.html'
})
export class MarketOrderMySuffixDialogComponent implements OnInit {

    marketOrder: MarketOrderMySuffix;
    authorities: any[];
    isSaving: boolean;

    payments: PaymentMySuffix[];

    customers: CustomerMySuffix[];
    orderDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketOrderService: MarketOrderMySuffixService,
        private paymentService: PaymentMySuffixService,
        private customerService: CustomerMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.paymentService.query({filter: 'order-is-null'}).subscribe((res: Response) => {
            if (!this.marketOrder.paymentId) {
                this.payments = res.json();
            } else {
                this.paymentService.find(this.marketOrder.paymentId).subscribe((subRes: PaymentMySuffix) => {
                    this.payments = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.customerService.query().subscribe(
            (res: Response) => { this.customers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketOrderService.update(this.marketOrder));
        } else {
            this.subscribeToSaveResponse(
                this.marketOrderService.create(this.marketOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketOrderMySuffix>) {
        result.subscribe((res: MarketOrderMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketOrderMySuffix) {
        this.eventManager.broadcast({ name: 'marketOrderListModification', content: 'OK'});
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

    trackPaymentById(index: number, item: PaymentMySuffix) {
        return item.id;
    }

    trackCustomerById(index: number, item: CustomerMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-order-my-suffix-popup',
    template: ''
})
export class MarketOrderMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderPopupService: MarketOrderMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.marketOrderPopupService
                    .open(MarketOrderMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.marketOrderPopupService
                    .open(MarketOrderMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
