import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketInfo } from './market-info.model';
import { MarketInfoPopupService } from './market-info-popup.service';
import { MarketInfoService } from './market-info.service';

@Component({
    selector: 'jhi-market-info-delete-dialog',
    templateUrl: './market-info-delete-dialog.component.html'
})
export class MarketInfoDeleteDialogComponent {

    marketInfo: MarketInfo;

    constructor(
        private marketInfoService: MarketInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
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
    selector: 'jhi-market-info-delete-popup',
    template: ''
})
export class MarketInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketInfoPopupService: MarketInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketInfoPopupService
                .open(MarketInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
