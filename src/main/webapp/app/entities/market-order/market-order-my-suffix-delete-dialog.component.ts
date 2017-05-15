import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MarketOrderMySuffix } from './market-order-my-suffix.model';
import { MarketOrderMySuffixPopupService } from './market-order-my-suffix-popup.service';
import { MarketOrderMySuffixService } from './market-order-my-suffix.service';

@Component({
    selector: 'jhi-market-order-my-suffix-delete-dialog',
    templateUrl: './market-order-my-suffix-delete-dialog.component.html'
})
export class MarketOrderMySuffixDeleteDialogComponent {

    marketOrder: MarketOrderMySuffix;

    constructor(
        private marketOrderService: MarketOrderMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketOrderListModification',
                content: 'Deleted an marketOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-order-my-suffix-delete-popup',
    template: ''
})
export class MarketOrderMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderPopupService: MarketOrderMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketOrderPopupService
                .open(MarketOrderMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
