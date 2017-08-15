import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProductCategory } from './product-category.model';
import { ProductCategoryPopupService } from './product-category-popup.service';
import { ProductCategoryService } from './product-category.service';
import { Market, MarketService } from '../market';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-product-category-dialog',
    templateUrl: './product-category-dialog.component.html'
})
export class ProductCategoryDialogComponent implements OnInit {

    productCategory: ProductCategory;
    isSaving: boolean;

    markets: Market[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private productCategoryService: ProductCategoryService,
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
        if (this.productCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productCategoryService.update(this.productCategory));
        } else {
            this.subscribeToSaveResponse(
                this.productCategoryService.create(this.productCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProductCategory>) {
        result.subscribe((res: ProductCategory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProductCategory) {
        this.eventManager.broadcast({ name: 'productCategoryListModification', content: 'OK'});
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
    selector: 'jhi-product-category-popup',
    template: ''
})
export class ProductCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCategoryPopupService: ProductCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productCategoryPopupService
                    .open(ProductCategoryDialogComponent as Component, params['id']);
            } else {
                this.productCategoryPopupService
                    .open(ProductCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
