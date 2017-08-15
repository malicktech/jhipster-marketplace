import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Invoices } from './invoices.model';
import { InvoicesPopupService } from './invoices-popup.service';
import { InvoicesService } from './invoices.service';

@Component({
    selector: 'jhi-invoices-dialog',
    templateUrl: './invoices-dialog.component.html'
})
export class InvoicesDialogComponent implements OnInit {

    invoices: Invoices;
    isSaving: boolean;
    invoiceDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private invoicesService: InvoicesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.invoices.id !== undefined) {
            this.subscribeToSaveResponse(
                this.invoicesService.update(this.invoices));
        } else {
            this.subscribeToSaveResponse(
                this.invoicesService.create(this.invoices));
        }
    }

    private subscribeToSaveResponse(result: Observable<Invoices>) {
        result.subscribe((res: Invoices) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Invoices) {
        this.eventManager.broadcast({ name: 'invoicesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-invoices-popup',
    template: ''
})
export class InvoicesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invoicesPopupService: InvoicesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.invoicesPopupService
                    .open(InvoicesDialogComponent as Component, params['id']);
            } else {
                this.invoicesPopupService
                    .open(InvoicesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
