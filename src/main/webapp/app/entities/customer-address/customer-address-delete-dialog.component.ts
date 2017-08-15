import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerAddress } from './customer-address.model';
import { CustomerAddressPopupService } from './customer-address-popup.service';
import { CustomerAddressService } from './customer-address.service';

@Component({
    selector: 'jhi-customer-address-delete-dialog',
    templateUrl: './customer-address-delete-dialog.component.html'
})
export class CustomerAddressDeleteDialogComponent {

    customerAddress: CustomerAddress;

    constructor(
        private customerAddressService: CustomerAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
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
    selector: 'jhi-customer-address-delete-popup',
    template: ''
})
export class CustomerAddressDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAddressPopupService: CustomerAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.customerAddressPopupService
                .open(CustomerAddressDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
