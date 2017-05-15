import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MarketInfoMySuffix } from './market-info-my-suffix.model';
import { MarketInfoMySuffixPopupService } from './market-info-my-suffix-popup.service';
import { MarketInfoMySuffixService } from './market-info-my-suffix.service';
import { MarketMySuffix, MarketMySuffixService } from '../market';

@Component({
    selector: 'jhi-market-info-my-suffix-dialog',
    templateUrl: './market-info-my-suffix-dialog.component.html'
})
export class MarketInfoMySuffixDialogComponent implements OnInit {

    marketInfo: MarketInfoMySuffix;
    authorities: any[];
    isSaving: boolean;

    markets: MarketMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketInfoService: MarketInfoMySuffixService,
        private marketService: MarketMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.marketService.query().subscribe(
            (res: Response) => { this.markets = res.json(); }, (res: Response) => this.onError(res.json()));
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

    private subscribeToSaveResponse(result: Observable<MarketInfoMySuffix>) {
        result.subscribe((res: MarketInfoMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketInfoMySuffix) {
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

    trackMarketById(index: number, item: MarketMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-info-my-suffix-popup',
    template: ''
})
export class MarketInfoMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketInfoPopupService: MarketInfoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.marketInfoPopupService
                    .open(MarketInfoMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.marketInfoPopupService
                    .open(MarketInfoMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
