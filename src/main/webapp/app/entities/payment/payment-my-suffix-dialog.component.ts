import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PaymentMySuffix } from './payment-my-suffix.model';
import { PaymentMySuffixPopupService } from './payment-my-suffix-popup.service';
import { PaymentMySuffixService } from './payment-my-suffix.service';
import { MarketOrderMySuffix, MarketOrderMySuffixService } from '../market-order';

@Component({
    selector: 'jhi-payment-my-suffix-dialog',
    templateUrl: './payment-my-suffix-dialog.component.html'
})
export class PaymentMySuffixDialogComponent implements OnInit {

    payment: PaymentMySuffix;
    authorities: any[];
    isSaving: boolean;

    marketorders: MarketOrderMySuffix[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private paymentService: PaymentMySuffixService,
        private marketOrderService: MarketOrderMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.marketOrderService.query().subscribe(
            (res: Response) => { this.marketorders = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.payment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paymentService.update(this.payment));
        } else {
            this.subscribeToSaveResponse(
                this.paymentService.create(this.payment));
        }
    }

    private subscribeToSaveResponse(result: Observable<PaymentMySuffix>) {
        result.subscribe((res: PaymentMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PaymentMySuffix) {
        this.eventManager.broadcast({ name: 'paymentListModification', content: 'OK'});
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

    trackMarketOrderById(index: number, item: MarketOrderMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-payment-my-suffix-popup',
    template: ''
})
export class PaymentMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentPopupService: PaymentMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.paymentPopupService
                    .open(PaymentMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.paymentPopupService
                    .open(PaymentMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
