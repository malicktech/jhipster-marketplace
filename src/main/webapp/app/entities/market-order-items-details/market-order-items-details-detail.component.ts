import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MarketOrderItemsDetails } from './market-order-items-details.model';
import { MarketOrderItemsDetailsService } from './market-order-items-details.service';

@Component({
    selector: 'jhi-market-order-items-details-detail',
    templateUrl: './market-order-items-details-detail.component.html'
})
export class MarketOrderItemsDetailsDetailComponent implements OnInit, OnDestroy {

    marketOrderItemsDetails: MarketOrderItemsDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketOrderItemsDetailsService: MarketOrderItemsDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketOrderItemsDetails();
    }

    load(id) {
        this.marketOrderItemsDetailsService.find(id).subscribe((marketOrderItemsDetails) => {
            this.marketOrderItemsDetails = marketOrderItemsDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketOrderItemsDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketOrderItemsDetailsListModification',
            (response) => this.load(this.marketOrderItemsDetails.id)
        );
    }
}
