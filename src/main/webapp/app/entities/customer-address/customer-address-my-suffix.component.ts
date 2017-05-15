import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { CustomerAddressMySuffix } from './customer-address-my-suffix.model';
import { CustomerAddressMySuffixService } from './customer-address-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-customer-address-my-suffix',
    templateUrl: './customer-address-my-suffix.component.html'
})
export class CustomerAddressMySuffixComponent implements OnInit, OnDestroy {
customerAddresses: CustomerAddressMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private customerAddressService: CustomerAddressMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.customerAddressService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.customerAddresses = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.customerAddressService.query().subscribe(
            (res: Response) => {
                this.customerAddresses = res.json();
                this.currentSearch = '';
            },
            (res: Response) => this.onError(res.json())
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCustomerAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CustomerAddressMySuffix) {
        return item.id;
    }
    registerChangeInCustomerAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('customerAddressListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
