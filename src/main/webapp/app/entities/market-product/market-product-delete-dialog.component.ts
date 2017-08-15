import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketProduct } from './market-product.model';
import { MarketProductPopupService } from './market-product-popup.service';
import { MarketProductService } from './market-product.service';

@Component({
    selector: 'jhi-market-product-delete-dialog',
    templateUrl: './market-product-delete-dialog.component.html'
})
export class MarketProductDeleteDialogComponent {

    marketProduct: MarketProduct;

    constructor(
        private marketProductService: MarketProductService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketProductService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketProductListModification',
                content: 'Deleted an marketProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-product-delete-popup',
    template: ''
})
export class MarketProductDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketProductPopupService: MarketProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketProductPopupService
                .open(MarketProductDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
