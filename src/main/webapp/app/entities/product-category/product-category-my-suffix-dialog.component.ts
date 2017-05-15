import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ProductCategoryMySuffix } from './product-category-my-suffix.model';
import { ProductCategoryMySuffixPopupService } from './product-category-my-suffix-popup.service';
import { ProductCategoryMySuffixService } from './product-category-my-suffix.service';
import { MarketMySuffix, MarketMySuffixService } from '../market';

@Component({
    selector: 'jhi-product-category-my-suffix-dialog',
    templateUrl: './product-category-my-suffix-dialog.component.html'
})
export class ProductCategoryMySuffixDialogComponent implements OnInit {

    productCategory: ProductCategoryMySuffix;
    authorities: any[];
    isSaving: boolean;

    markets: MarketMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private productCategoryService: ProductCategoryMySuffixService,
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
        if (this.productCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productCategoryService.update(this.productCategory));
        } else {
            this.subscribeToSaveResponse(
                this.productCategoryService.create(this.productCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProductCategoryMySuffix>) {
        result.subscribe((res: ProductCategoryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProductCategoryMySuffix) {
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

    trackMarketById(index: number, item: MarketMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-category-my-suffix-popup',
    template: ''
})
export class ProductCategoryMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCategoryPopupService: ProductCategoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productCategoryPopupService
                    .open(ProductCategoryMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.productCategoryPopupService
                    .open(ProductCategoryMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
