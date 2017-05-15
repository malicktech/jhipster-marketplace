import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MarketOrderItemsMySuffix } from './market-order-items-my-suffix.model';
import { MarketOrderItemsMySuffixPopupService } from './market-order-items-my-suffix-popup.service';
import { MarketOrderItemsMySuffixService } from './market-order-items-my-suffix.service';

@Component({
    selector: 'jhi-market-order-items-my-suffix-delete-dialog',
    templateUrl: './market-order-items-my-suffix-delete-dialog.component.html'
})
export class MarketOrderItemsMySuffixDeleteDialogComponent {

    marketOrderItems: MarketOrderItemsMySuffix;

    constructor(
        private marketOrderItemsService: MarketOrderItemsMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketOrderItemsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketOrderItemsListModification',
                content: 'Deleted an marketOrderItems'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-order-items-my-suffix-delete-popup',
    template: ''
})
export class MarketOrderItemsMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderItemsPopupService: MarketOrderItemsMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketOrderItemsPopupService
                .open(MarketOrderItemsMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
