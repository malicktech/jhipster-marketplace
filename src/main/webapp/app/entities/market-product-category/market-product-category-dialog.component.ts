import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarketProductCategory } from './market-product-category.model';
import { MarketProductCategoryPopupService } from './market-product-category-popup.service';
import { MarketProductCategoryService } from './market-product-category.service';

@Component({
    selector: 'jhi-market-product-category-dialog',
    templateUrl: './market-product-category-dialog.component.html'
})
export class MarketProductCategoryDialogComponent implements OnInit {

    marketProductCategory: MarketProductCategory;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private marketProductCategoryService: MarketProductCategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketProductCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketProductCategoryService.update(this.marketProductCategory));
        } else {
            this.subscribeToSaveResponse(
                this.marketProductCategoryService.create(this.marketProductCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketProductCategory>) {
        result.subscribe((res: MarketProductCategory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketProductCategory) {
        this.eventManager.broadcast({ name: 'marketProductCategoryListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-market-product-category-popup',
    template: ''
})
export class MarketProductCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketProductCategoryPopupService: MarketProductCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketProductCategoryPopupService
                    .open(MarketProductCategoryDialogComponent as Component, params['id']);
            } else {
                this.marketProductCategoryPopupService
                    .open(MarketProductCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
