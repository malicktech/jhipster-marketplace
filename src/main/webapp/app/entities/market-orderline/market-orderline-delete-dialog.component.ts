import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketOrderline } from './market-orderline.model';
import { MarketOrderlinePopupService } from './market-orderline-popup.service';
import { MarketOrderlineService } from './market-orderline.service';

@Component({
    selector: 'jhi-market-orderline-delete-dialog',
    templateUrl: './market-orderline-delete-dialog.component.html'
})
export class MarketOrderlineDeleteDialogComponent {

    marketOrderline: MarketOrderline;

    constructor(
        private marketOrderlineService: MarketOrderlineService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketOrderlineService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketOrderlineListModification',
                content: 'Deleted an marketOrderline'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-orderline-delete-popup',
    template: ''
})
export class MarketOrderlineDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOrderlinePopupService: MarketOrderlinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketOrderlinePopupService
                .open(MarketOrderlineDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
