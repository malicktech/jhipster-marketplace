import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CustomerAddressMySuffix } from './customer-address-my-suffix.model';
import { CustomerAddressMySuffixPopupService } from './customer-address-my-suffix-popup.service';
import { CustomerAddressMySuffixService } from './customer-address-my-suffix.service';

@Component({
    selector: 'jhi-customer-address-my-suffix-delete-dialog',
    templateUrl: './customer-address-my-suffix-delete-dialog.component.html'
})
export class CustomerAddressMySuffixDeleteDialogComponent {

    customerAddress: CustomerAddressMySuffix;

    constructor(
        private customerAddressService: CustomerAddressMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerAddressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerAddressListModification',
                content: 'Deleted an customerAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-address-my-suffix-delete-popup',
    template: ''
})
export class CustomerAddressMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAddressPopupService: CustomerAddressMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerAddressPopupService
                .open(CustomerAddressMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
