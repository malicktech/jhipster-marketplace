import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarketOrderItemsDetails } from './market-order-items-details.model';
import { MarketOrderItemsDetailsPopupService } from './market-order-items-details-popup.service';
import { MarketOrderItemsDetailsService } from './market-order-items-details.service';
import { MarketOrderline, MarketOrderlineService } from '../market-orderline';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-market-order-items-details-dialog',
    templateUrl: './market-order-items-details-dialog.component.html'
})
export class MarketOrderItemsDetailsDialogComponent implements OnInit {

    marketOrderItemsDetails: MarketOrderItemsDetails;
    isSaving: boolean;

    marketorderlines: MarketOrderline[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private marketOrderItemsDetailsService: MarketOrderItemsDetailsService,
        private marketOrderlineService: MarketOrderlineService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marketOrderlineService.query()
            .subscribe((res: ResponseWrapper) => { this.marketorderlines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketOrderItemsDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketOrderItemsDetailsService.update(this.marketOrderItemsDetails));
        } else {
            this.subscribeToSaveResponse(
                this.marketOrderItemsDetailsService.create(this.marketOrderItemsDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketOrderItemsDetails>) {
        result.subscribe((res: MarketOrderItemsDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketOrderItemsDetails) {
        this.eventManager.broadcast({ name: 'marketOrderItemsDetailsListModification', content: 'OK'});
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

    trackMarketOrderlineById(index: number, item: MarketOrderline) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-order-items-details-popup',
    template: ''
})
export class MarketOrderItemsDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderItemsDetailsPopupService: MarketOrderItemsDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketOrderItemsDetailsPopupService
                    .open(MarketOrderItemsDetailsDialogComponent as Component, params['id']);
            } else {
                this.marketOrderItemsDetailsPopupService
                    .open(MarketOrderItemsDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
