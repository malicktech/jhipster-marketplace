import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { MarketOrderItemsMySuffix } from './market-order-items-my-suffix.model';
import { MarketOrderItemsMySuffixService } from './market-order-items-my-suffix.service';

@Component({
    selector: 'jhi-market-order-items-my-suffix-detail',
    templateUrl: './market-order-items-my-suffix-detail.component.html'
})
export class MarketOrderItemsMySuffixDetailComponent implements OnInit, OnDestroy {

    marketOrderItems: MarketOrderItemsMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private marketOrderItemsService: MarketOrderItemsMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketOrderItems();
    }

    load(id) {
        this.marketOrderItemsService.find(id).subscribe((marketOrderItems) => {
            this.marketOrderItems = marketOrderItems;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketOrderItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketOrderItemsListModification',
            (response) => this.load(this.marketOrderItems.id)
        );
    }
}
