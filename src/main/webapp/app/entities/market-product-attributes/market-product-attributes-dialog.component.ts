import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarketProductAttributes } from './market-product-attributes.model';
import { MarketProductAttributesPopupService } from './market-product-attributes-popup.service';
import { MarketProductAttributesService } from './market-product-attributes.service';
import { MarketProduct, MarketProductService } from '../market-product';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-market-product-attributes-dialog',
    templateUrl: './market-product-attributes-dialog.component.html'
})
export class MarketProductAttributesDialogComponent implements OnInit {

    marketProductAttributes: MarketProductAttributes;
    isSaving: boolean;

    marketproducts: MarketProduct[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private marketProductAttributesService: MarketProductAttributesService,
        private marketProductService: MarketProductService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marketProductService.query()
            .subscribe((res: ResponseWrapper) => { this.marketproducts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketProductAttributes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketProductAttributesService.update(this.marketProductAttributes));
        } else {
            this.subscribeToSaveResponse(
                this.marketProductAttributesService.create(this.marketProductAttributes));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketProductAttributes>) {
        result.subscribe((res: MarketProductAttributes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketProductAttributes) {
        this.eventManager.broadcast({ name: 'marketProductAttributesListModification', content: 'OK'});
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

    trackMarketProductById(index: number, item: MarketProduct) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-product-attributes-popup',
    template: ''
})
export class MarketProductAttributesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketProductAttributesPopupService: MarketProductAttributesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketProductAttributesPopupService
                    .open(MarketProductAttributesDialogComponent as Component, params['id']);
            } else {
                this.marketProductAttributesPopupService
                    .open(MarketProductAttributesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
