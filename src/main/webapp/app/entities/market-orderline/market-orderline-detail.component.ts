import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MarketOrderline } from './market-orderline.model';
import { MarketOrderlineService } from './market-orderline.service';

@Component({
    selector: 'jhi-market-orderline-detail',
    templateUrl: './market-orderline-detail.component.html'
})
export class MarketOrderlineDetailComponent implements OnInit, OnDestroy {

    marketOrderline: MarketOrderline;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketOrderlineService: MarketOrderlineService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketOrderlines();
    }

    load(id) {
        this.marketOrderlineService.find(id).subscribe((marketOrderline) => {
            this.marketOrderline = marketOrderline;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketOrderlines() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketOrderlineListModification',
            (response) => this.load(this.marketOrderline.id)
        );
    }
}
