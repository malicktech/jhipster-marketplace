import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketProductAttributes } from './market-product-attributes.model';
import { MarketProductAttributesPopupService } from './market-product-attributes-popup.service';
import { MarketProductAttributesService } from './market-product-attributes.service';

@Component({
    selector: 'jhi-market-product-attributes-delete-dialog',
    templateUrl: './market-product-attributes-delete-dialog.component.html'
})
export class MarketProductAttributesDeleteDialogComponent {

    marketProductAttributes: MarketProductAttributes;

    constructor(
        private marketProductAttributesService: MarketProductAttributesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketProductAttributesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketProductAttributesListModification',
                content: 'Deleted an marketProductAttributes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-product-attributes-delete-popup',
    template: ''
})
export class MarketProductAttributesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketProductAttributesPopupService: MarketProductAttributesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketProductAttributesPopupService
                .open(MarketProductAttributesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
