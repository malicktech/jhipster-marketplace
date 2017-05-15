import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { MarketOrderItemsMySuffix } from './market-order-items-my-suffix.model';
import { MarketOrderItemsMySuffixPopupService } from './market-order-items-my-suffix-popup.service';
import { MarketOrderItemsMySuffixService } from './market-order-items-my-suffix.service';
import { MarketOrderMySuffix, MarketOrderMySuffixService } from '../market-order';

@Component({
    selector: 'jhi-market-order-items-my-suffix-dialog',
    templateUrl: './market-order-items-my-suffix-dialog.component.html'
})
export class MarketOrderItemsMySuffixDialogComponent implements OnInit {

    marketOrderItems: MarketOrderItemsMySuffix;
    authorities: any[];
    isSaving: boolean;

    marketorders: MarketOrderMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private marketOrderItemsService: MarketOrderItemsMySuffixService,
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
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, marketOrderItems, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                marketOrderItems[field] = base64Data;
                marketOrderItems[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketOrderItems.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketOrderItemsService.update(this.marketOrderItems));
        } else {
            this.subscribeToSaveResponse(
                this.marketOrderItemsService.create(this.marketOrderItems));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketOrderItemsMySuffix>) {
        result.subscribe((res: MarketOrderItemsMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketOrderItemsMySuffix) {
        this.eventManager.broadcast({ name: 'marketOrderItemsListModification', content: 'OK'});
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
    selector: 'jhi-market-order-items-my-suffix-popup',
    template: ''
})
export class MarketOrderItemsMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderItemsPopupService: MarketOrderItemsMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.marketOrderItemsPopupService
                    .open(MarketOrderItemsMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.marketOrderItemsPopupService
                    .open(MarketOrderItemsMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
