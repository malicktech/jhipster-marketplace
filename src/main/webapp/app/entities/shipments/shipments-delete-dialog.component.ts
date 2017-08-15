import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Shipments } from './shipments.model';
import { ShipmentsPopupService } from './shipments-popup.service';
import { ShipmentsService } from './shipments.service';

@Component({
    selector: 'jhi-shipments-delete-dialog',
    templateUrl: './shipments-delete-dialog.component.html'
})
export class ShipmentsDeleteDialogComponent {

    shipments: Shipments;

    constructor(
        private shipmentsService: ShipmentsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shipmentsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shipmentsListModification',
                content: 'Deleted an shipments'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shipments-delete-popup',
    template: ''
})
export class ShipmentsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipmentsPopupService: ShipmentsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shipmentsPopupService
                .open(ShipmentsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
