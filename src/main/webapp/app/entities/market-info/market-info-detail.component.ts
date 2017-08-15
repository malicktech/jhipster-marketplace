import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MarketInfo } from './market-info.model';
import { MarketInfoService } from './market-info.service';

@Component({
    selector: 'jhi-market-info-detail',
    templateUrl: './market-info-detail.component.html'
})
export class MarketInfoDetailComponent implements OnInit, OnDestroy {

    marketInfo: MarketInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketInfoService: MarketInfoService,
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
