import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MarketProductCategory } from './market-product-category.model';
import { MarketProductCategoryService } from './market-product-category.service';

@Component({
    selector: 'jhi-market-product-category-detail',
    templateUrl: './market-product-category-detail.component.html'
})
export class MarketProductCategoryDetailComponent implements OnInit, OnDestroy {

    marketProductCategory: MarketProductCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketProductCategoryService: MarketProductCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketProductCategories();
    }

    load(id) {
        this.marketProductCategoryService.find(id).subscribe((marketProductCategory) => {
            this.marketProductCategory = marketProductCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketProductCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketProductCategoryListModification',
            (response) => this.load(this.marketProductCategory.id)
        );
    }
}
