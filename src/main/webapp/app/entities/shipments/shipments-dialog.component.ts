import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Shipments } from './shipments.model';
import { ShipmentsPopupService } from './shipments-popup.service';
import { ShipmentsService } from './shipments.service';
import { MarketOrders, MarketOrdersService } from '../market-orders';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-shipments-dialog',
    templateUrl: './shipments-dialog.component.html'
})
export class ShipmentsDialogComponent implements OnInit {

    shipments: Shipments;
    isSaving: boolean;

    marketorders: MarketOrders[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private shipmentsService: ShipmentsService,
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
        if (this.shipments.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shipmentsService.update(this.shipments));
        } else {
            this.subscribeToSaveResponse(
                this.shipmentsService.create(this.shipments));
        }
    }

    private subscribeToSaveResponse(result: Observable<Shipments>) {
        result.subscribe((res: Shipments) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Shipments) {
        this.eventManager.broadcast({ name: 'shipmentsListModification', content: 'OK'});
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
    selector: 'jhi-shipments-popup',
    template: ''
})
export class ShipmentsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipmentsPopupService: ShipmentsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shipmentsPopupService
                    .open(ShipmentsDialogComponent as Component, params['id']);
            } else {
                this.shipmentsPopupService
                    .open(ShipmentsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
