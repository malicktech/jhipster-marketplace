import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { MarketMySuffix } from './market-my-suffix.model';
import { MarketMySuffixService } from './market-my-suffix.service';

@Component({
    selector: 'jhi-market-my-suffix-detail',
    templateUrl: './market-my-suffix-detail.component.html'
})
export class MarketMySuffixDetailComponent implements OnInit, OnDestroy {

    market: MarketMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private marketService: MarketMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarkets();
    }

    load(id) {
        this.marketService.find(id).subscribe((market) => {
            this.market = market;
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

    registerChangeInMarkets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketListModification',
            (response) => this.load(this.market.id)
        );
    }
}
