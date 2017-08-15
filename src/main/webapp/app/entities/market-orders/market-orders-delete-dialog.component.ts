import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketOrders } from './market-orders.model';
import { MarketOrdersPopupService } from './market-orders-popup.service';
import { MarketOrdersService } from './market-orders.service';

@Component({
    selector: 'jhi-market-orders-delete-dialog',
    templateUrl: './market-orders-delete-dialog.component.html'
})
export class MarketOrdersDeleteDialogComponent {

    marketOrders: MarketOrders;

    constructor(
        private marketOrdersService: MarketOrdersService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketOrdersService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketOrdersListModification',
                content: 'Deleted an marketOrders'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-orders-delete-popup',
    template: ''
})
export class MarketOrdersDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrdersPopupService: MarketOrdersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketOrdersPopupService
                .open(MarketOrdersDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
