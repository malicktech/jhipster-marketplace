import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CustomerAddress } from './customer-address.model';
import { CustomerAddressPopupService } from './customer-address-popup.service';
import { CustomerAddressService } from './customer-address.service';
import { Country, CountryService } from '../country';
import { Customer, CustomerService } from '../customer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-customer-address-dialog',
    templateUrl: './customer-address-dialog.component.html'
})
export class CustomerAddressDialogComponent implements OnInit {

    customerAddress: CustomerAddress;
    isSaving: boolean;

    countries: Country[];

    customers: Customer[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private customerAddressService: CustomerAddressService,
        private countryService: CountryService,
        private customerService: CustomerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.countryService
            .query({filter: 'customeraddress-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.customerAddress.countryId) {
                    this.countries = res.json;
                } else {
                    this.countryService
                        .find(this.customerAddress.countryId)
                        .subscribe((subRes: Country) => {
                            this.countries = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.customerService.query()
            .subscribe((res: ResponseWrapper) => { this.customers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    private subscribeToSaveResponse(result: Observable<CustomerAddress>) {
        result.subscribe((res: CustomerAddress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CustomerAddress) {
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

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-address-popup',
    template: ''
})
export class CustomerAddressPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAddressPopupService: CustomerAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.customerAddressPopupService
                    .open(CustomerAddressDialogComponent as Component, params['id']);
            } else {
                this.customerAddressPopupService
                    .open(CustomerAddressDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
