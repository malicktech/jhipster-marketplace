import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CustomerAddress } from './customer-address.model';
import { CustomerAddressService } from './customer-address.service';

@Component({
    selector: 'jhi-customer-address-detail',
    templateUrl: './customer-address-detail.component.html'
})
export class CustomerAddressDetailComponent implements OnInit, OnDestroy {

    customerAddress: CustomerAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private customerAddressService: CustomerAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerAddresses();
    }

    load(id) {
        this.customerAddressService.find(id).subscribe((customerAddress) => {
            this.customerAddress = customerAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'customerAddressListModification',
            (response) => this.load(this.customerAddress.id)
        );
    }
}
