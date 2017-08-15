import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketOrderItemsDetails } from './market-order-items-details.model';
import { MarketOrderItemsDetailsPopupService } from './market-order-items-details-popup.service';
import { MarketOrderItemsDetailsService } from './market-order-items-details.service';

@Component({
    selector: 'jhi-market-order-items-details-delete-dialog',
    templateUrl: './market-order-items-details-delete-dialog.component.html'
})
export class MarketOrderItemsDetailsDeleteDialogComponent {

    marketOrderItemsDetails: MarketOrderItemsDetails;

    constructor(
        private marketOrderItemsDetailsService: MarketOrderItemsDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketOrderItemsDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketOrderItemsDetailsListModification',
                content: 'Deleted an marketOrderItemsDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-order-items-details-delete-popup',
    template: ''
})
export class MarketOrderItemsDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderItemsDetailsPopupService: MarketOrderItemsDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketOrderItemsDetailsPopupService
                .open(MarketOrderItemsDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
