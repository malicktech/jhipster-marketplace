import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerAddressMySuffix } from './customer-address-my-suffix.model';
import { CustomerAddressMySuffixService } from './customer-address-my-suffix.service';
@Injectable()
export class CustomerAddressMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerAddressService: CustomerAddressMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerAddressService.find(id).subscribe((customerAddress) => {
                this.customerAddressModalRef(component, customerAddress);
            });
        } else {
            return this.customerAddressModalRef(component, new CustomerAddressMySuffix());
        }
    }

    customerAddressModalRef(component: Component, customerAddress: CustomerAddressMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerAddress = customerAddress;
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
