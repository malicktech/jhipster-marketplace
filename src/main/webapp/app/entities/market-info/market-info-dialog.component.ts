import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarketInfo } from './market-info.model';
import { MarketInfoPopupService } from './market-info-popup.service';
import { MarketInfoService } from './market-info.service';
import { Market, MarketService } from '../market';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-market-info-dialog',
    templateUrl: './market-info-dialog.component.html'
})
export class MarketInfoDialogComponent implements OnInit {

    marketInfo: MarketInfo;
    isSaving: boolean;

    markets: Market[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private marketInfoService: MarketInfoService,
        private marketService: MarketService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marketService.query()
            .subscribe((res: ResponseWrapper) => { this.markets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketInfoService.update(this.marketInfo));
        } else {
            this.subscribeToSaveResponse(
                this.marketInfoService.create(this.marketInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketInfo>) {
        result.subscribe((res: MarketInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketInfo) {
        this.eventManager.broadcast({ name: 'marketInfoListModification', content: 'OK'});
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

    trackMarketById(index: number, item: Market) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-info-popup',
    template: ''
})
export class MarketInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketInfoPopupService: MarketInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketInfoPopupService
                    .open(MarketInfoDialogComponent as Component, params['id']);
            } else {
                this.marketInfoPopupService
                    .open(MarketInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
