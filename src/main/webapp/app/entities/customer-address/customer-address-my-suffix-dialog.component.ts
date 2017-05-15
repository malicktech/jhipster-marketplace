import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CustomerAddressMySuffix } from './customer-address-my-suffix.model';
import { CustomerAddressMySuffixPopupService } from './customer-address-my-suffix-popup.service';
import { CustomerAddressMySuffixService } from './customer-address-my-suffix.service';
import { CountryMySuffix, CountryMySuffixService } from '../country';
import { CustomerMySuffix, CustomerMySuffixService } from '../customer';

@Component({
    selector: 'jhi-customer-address-my-suffix-dialog',
    templateUrl: './customer-address-my-suffix-dialog.component.html'
})
export class CustomerAddressMySuffixDialogComponent implements OnInit {

    customerAddress: CustomerAddressMySuffix;
    authorities: any[];
    isSaving: boolean;

    countries: CountryMySuffix[];

    customers: CustomerMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private customerAddressService: CustomerAddressMySuffixService,
        private countryService: CountryMySuffixService,
        private customerService: CustomerMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.countryService.query({filter: 'customeraddress-is-null'}).subscribe((res: Response) => {
            if (!this.customerAddress.countryId) {
                this.countries = res.json();
            } else {
                this.countryService.find(this.customerAddress.countryId).subscribe((subRes: CountryMySuffix) => {
                    this.countries = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.customerService.query().subscribe(
            (res: Response) => { this.customers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customerAddress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.customerAddressService.update(this.customerAddress));
        } else {
            this.subscribeToSaveResponse(
                this.customerAddressService.create(this.customerAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<CustomerAddressMySuffix>) {
        result.subscribe((res: CustomerAddressMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CustomerAddressMySuffix) {
        this.eventManager.broadcast({ name: 'customerAddressListModification', content: 'OK'});
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

    trackCountryById(index: number, item: CountryMySuffix) {
        return item.id;
    }

    trackCustomerById(index: number, item: CustomerMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-address-my-suffix-popup',
    template: ''
})
export class CustomerAddressMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAddressPopupService: CustomerAddressMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerAddressPopupService
                    .open(CustomerAddressMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerAddressPopupService
                    .open(CustomerAddressMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
