import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Invoices } from './invoices.model';
import { InvoicesPopupService } from './invoices-popup.service';
import { InvoicesService } from './invoices.service';

@Component({
    selector: 'jhi-invoices-delete-dialog',
    templateUrl: './invoices-delete-dialog.component.html'
})
export class InvoicesDeleteDialogComponent {

    invoices: Invoices;

    constructor(
        private invoicesService: InvoicesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invoicesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'invoicesListModification',
                content: 'Deleted an invoices'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invoices-delete-popup',
    template: ''
})
export class InvoicesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invoicesPopupService: InvoicesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.invoicesPopupService
                .open(InvoicesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
