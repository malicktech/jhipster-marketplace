import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketProductCategory } from './market-product-category.model';
import { MarketProductCategoryPopupService } from './market-product-category-popup.service';
import { MarketProductCategoryService } from './market-product-category.service';

@Component({
    selector: 'jhi-market-product-category-delete-dialog',
    templateUrl: './market-product-category-delete-dialog.component.html'
})
export class MarketProductCategoryDeleteDialogComponent {

    marketProductCategory: MarketProductCategory;

    constructor(
        private marketProductCategoryService: MarketProductCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketProductCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketProductCategoryListModification',
                content: 'Deleted an marketProductCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-product-category-delete-popup',
    template: ''
})
export class MarketProductCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketProductCategoryPopupService: MarketProductCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketProductCategoryPopupService
                .open(MarketProductCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
