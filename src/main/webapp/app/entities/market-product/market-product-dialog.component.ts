import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MarketProduct } from './market-product.model';
import { MarketProductPopupService } from './market-product-popup.service';
import { MarketProductService } from './market-product.service';
import { MarketOrderline, MarketOrderlineService } from '../market-orderline';
import { MarketProductCategory, MarketProductCategoryService } from '../market-product-category';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-market-product-dialog',
    templateUrl: './market-product-dialog.component.html'
})
export class MarketProductDialogComponent implements OnInit {

    marketProduct: MarketProduct;
    isSaving: boolean;

    marketorderlines: MarketOrderline[];

    marketproductcategories: MarketProductCategory[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private marketProductService: MarketProductService,
        private marketOrderlineService: MarketOrderlineService,
        private marketProductCategoryService: MarketProductCategoryService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marketOrderlineService.query()
            .subscribe((res: ResponseWrapper) => { this.marketorderlines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.marketProductCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.marketproductcategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, marketProduct, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                marketProduct[field] = base64Data;
                marketProduct[`${field}ContentType`] = file.type;
            });
        }
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.marketProduct, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketProduct.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketProductService.update(this.marketProduct));
        } else {
            this.subscribeToSaveResponse(
                this.marketProductService.create(this.marketProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketProduct>) {
        result.subscribe((res: MarketProduct) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketProduct) {
        this.eventManager.broadcast({ name: 'marketProductListModification', content: 'OK'});
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

    trackMarketProductCategoryById(index: number, item: MarketProductCategory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-product-popup',
    template: ''
})
export class MarketProductPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketProductPopupService: MarketProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketProductPopupService
                    .open(MarketProductDialogComponent as Component, params['id']);
            } else {
                this.marketProductPopupService
                    .open(MarketProductDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
