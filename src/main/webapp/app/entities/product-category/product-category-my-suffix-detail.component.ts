import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ProductCategoryMySuffix } from './product-category-my-suffix.model';
import { ProductCategoryMySuffixService } from './product-category-my-suffix.service';

@Component({
    selector: 'jhi-product-category-my-suffix-detail',
    templateUrl: './product-category-my-suffix-detail.component.html'
})
export class ProductCategoryMySuffixDetailComponent implements OnInit, OnDestroy {

    productCategory: ProductCategoryMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private productCategoryService: ProductCategoryMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductCategories();
    }

    load(id) {
        this.productCategoryService.find(id).subscribe((productCategory) => {
            this.productCategory = productCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'productCategoryListModification',
            (response) => this.load(this.productCategory.id)
        );
    }
}
