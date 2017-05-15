import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MarketInfoMySuffix } from './market-info-my-suffix.model';
import { MarketInfoMySuffixPopupService } from './market-info-my-suffix-popup.service';
import { MarketInfoMySuffixService } from './market-info-my-suffix.service';

@Component({
    selector: 'jhi-market-info-my-suffix-delete-dialog',
    templateUrl: './market-info-my-suffix-delete-dialog.component.html'
})
export class MarketInfoMySuffixDeleteDialogComponent {

    marketInfo: MarketInfoMySuffix;

    constructor(
        private marketInfoService: MarketInfoMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketInfoListModification',
                content: 'Deleted an marketInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-info-my-suffix-delete-popup',
    template: ''
})
export class MarketInfoMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketInfoPopupService: MarketInfoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketInfoPopupService
                .open(MarketInfoMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
