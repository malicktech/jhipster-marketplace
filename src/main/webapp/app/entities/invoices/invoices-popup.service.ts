import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Invoices } from './invoices.model';
import { InvoicesService } from './invoices.service';

@Injectable()
export class InvoicesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private invoicesService: InvoicesService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.invoicesService.find(id).subscribe((invoices) => {
                    if (invoices.invoiceDate) {
                        invoices.invoiceDate = {
                            year: invoices.invoiceDate.getFullYear(),
                            month: invoices.invoiceDate.getMonth() + 1,
                            day: invoices.invoiceDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.invoicesModalRef(component, invoices);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.invoicesModalRef(component, new Invoices());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    invoicesModalRef(component: Component, invoices: Invoices): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.invoices = invoices;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
