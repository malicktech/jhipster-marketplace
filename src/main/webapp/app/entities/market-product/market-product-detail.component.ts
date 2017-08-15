import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { MarketProduct } from './market-product.model';
import { MarketProductService } from './market-product.service';

@Component({
    selector: 'jhi-market-product-detail',
    templateUrl: './market-product-detail.component.html'
})
export class MarketProductDetailComponent implements OnInit, OnDestroy {

    marketProduct: MarketProduct;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private marketProductService: MarketProductService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketProducts();
    }

    load(id) {
        this.marketProductService.find(id).subscribe((marketProduct) => {
            this.marketProduct = marketProduct;
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

    registerChangeInMarketProducts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketProductListModification',
            (response) => this.load(this.marketProduct.id)
        );
    }
}
