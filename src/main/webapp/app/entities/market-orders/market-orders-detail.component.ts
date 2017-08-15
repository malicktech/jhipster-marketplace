import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MarketOrders } from './market-orders.model';
import { MarketOrdersService } from './market-orders.service';

@Component({
    selector: 'jhi-market-orders-detail',
    templateUrl: './market-orders-detail.component.html'
})
export class MarketOrdersDetailComponent implements OnInit, OnDestroy {

    marketOrders: MarketOrders;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketOrdersService: MarketOrdersService,
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
        this.marketOrdersService.find(id).subscribe((marketOrders) => {
            this.marketOrders = marketOrders;
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
            'marketOrdersListModification',
            (response) => this.load(this.marketOrders.id)
        );
    }
}
