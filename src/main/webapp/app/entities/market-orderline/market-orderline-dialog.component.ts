import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarketOrderline } from './market-orderline.model';
import { MarketOrderlinePopupService } from './market-orderline-popup.service';
import { MarketOrderlineService } from './market-orderline.service';
import { MarketOrders, MarketOrdersService } from '../market-orders';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-market-orderline-dialog',
    templateUrl: './market-orderline-dialog.component.html'
})
export class MarketOrderlineDialogComponent implements OnInit {

    marketOrderline: MarketOrderline;
    isSaving: boolean;

    marketorders: MarketOrders[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private marketOrderlineService: MarketOrderlineService,
        private marketOrdersService: MarketOrdersService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marketOrdersService.query()
            .subscribe((res: ResponseWrapper) => { this.marketorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketOrderline.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketOrderlineService.update(this.marketOrderline));
        } else {
            this.subscribeToSaveResponse(
                this.marketOrderlineService.create(this.marketOrderline));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketOrderline>) {
        result.subscribe((res: MarketOrderline) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketOrderline) {
        this.eventManager.broadcast({ name: 'marketOrderlineListModification', content: 'OK'});
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

    trackMarketOrdersById(index: number, item: MarketOrders) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-orderline-popup',
    template: ''
})
export class MarketOrderlinePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderlinePopupService: MarketOrderlinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketOrderlinePopupService
                    .open(MarketOrderlineDialogComponent as Component, params['id']);
            } else {
                this.marketOrderlinePopupService
                    .open(MarketOrderlineDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
