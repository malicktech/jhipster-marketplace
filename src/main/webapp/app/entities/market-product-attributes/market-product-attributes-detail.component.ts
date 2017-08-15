import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MarketProductAttributes } from './market-product-attributes.model';
import { MarketProductAttributesService } from './market-product-attributes.service';

@Component({
    selector: 'jhi-market-product-attributes-detail',
    templateUrl: './market-product-attributes-detail.component.html'
})
export class MarketProductAttributesDetailComponent implements OnInit, OnDestroy {

    marketProductAttributes: MarketProductAttributes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketProductAttributesService: MarketProductAttributesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketProductAttributes();
    }

    load(id) {
        this.marketProductAttributesService.find(id).subscribe((marketProductAttributes) => {
            this.marketProductAttributes = marketProductAttributes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketProductAttributes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketProductAttributesListModification',
            (response) => this.load(this.marketProductAttributes.id)
        );
    }
}
