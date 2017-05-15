import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketOrderMySuffix } from './market-order-my-suffix.model';
import { MarketOrderMySuffixService } from './market-order-my-suffix.service';

@Component({
    selector: 'jhi-market-order-my-suffix-detail',
    templateUrl: './market-order-my-suffix-detail.component.html'
})
export class MarketOrderMySuffixDetailComponent implements OnInit, OnDestroy {

    marketOrder: MarketOrderMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketOrderService: MarketOrderMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketOrders();
    }

    load(id) {
        this.marketOrderService.find(id).subscribe((marketOrder) => {
            this.marketOrder = marketOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketOrderListModification',
            (response) => this.load(this.marketOrder.id)
        );
    }
}
