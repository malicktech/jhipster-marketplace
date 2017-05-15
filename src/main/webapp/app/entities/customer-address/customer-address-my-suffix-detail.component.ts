import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { CustomerAddressMySuffix } from './customer-address-my-suffix.model';
import { CustomerAddressMySuffixService } from './customer-address-my-suffix.service';

@Component({
    selector: 'jhi-customer-address-my-suffix-detail',
    templateUrl: './customer-address-my-suffix-detail.component.html'
})
export class CustomerAddressMySuffixDetailComponent implements OnInit, OnDestroy {

    customerAddress: CustomerAddressMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private customerAddressService: CustomerAddressMySuffixService,
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
