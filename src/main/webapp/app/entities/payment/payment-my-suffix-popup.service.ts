import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PaymentMySuffix } from './payment-my-suffix.model';
import { PaymentMySuffixService } from './payment-my-suffix.service';
@Injectable()
export class PaymentMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private paymentService: PaymentMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.paymentService.find(id).subscribe((payment) => {
                if (payment.date) {
                    payment.date = {
                        year: payment.date.getFullYear(),
                        month: payment.date.getMonth() + 1,
                        day: payment.date.getDate()
                    };
                }
                this.paymentModalRef(component, payment);
            });
        } else {
            return this.paymentModalRef(component, new PaymentMySuffix());
        }
    }

    paymentModalRef(component: Component, payment: PaymentMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payment = payment;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
