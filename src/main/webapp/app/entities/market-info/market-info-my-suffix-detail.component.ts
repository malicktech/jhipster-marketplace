import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketInfoMySuffix } from './market-info-my-suffix.model';
import { MarketInfoMySuffixService } from './market-info-my-suffix.service';

@Component({
    selector: 'jhi-market-info-my-suffix-detail',
    templateUrl: './market-info-my-suffix-detail.component.html'
})
export class MarketInfoMySuffixDetailComponent implements OnInit, OnDestroy {

    marketInfo: MarketInfoMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketInfoService: MarketInfoMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketInfos();
    }

    load(id) {
        this.marketInfoService.find(id).subscribe((marketInfo) => {
            this.marketInfo = marketInfo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketInfoListModification',
            (response) => this.load(this.marketInfo.id)
        );
    }
}
