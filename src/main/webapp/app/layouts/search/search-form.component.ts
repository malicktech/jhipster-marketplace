import { Component } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Formsearch } from './search-form';
import { ProductService } from '../../amazonitem/marketproduct/product.service';

@Component({
    selector: 'jhi-search-form',
    templateUrl: './search-form.component.html',
    styleUrls: [],
})
export class SearchFormComponent {

    markets = ['amazon', 'cdiscount'];
    searchindexes = ['All', 'Books', 'Electronics', 'PCHardware', 'Apparel'];

    model = new Formsearch(this.markets[0], this.searchindexes[0], 'java', '1');

    submitted = false;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private productService: ProductService
    ) { }

    onSubmit() {
        this.submitted = true;
        // console.log(this.model);
        this.productService.getProducts(this.model.market, this.model.searchindex, this.model.query, '1')
        .then((products) => { this.router.navigate(['/marketproduct', { market: this.model.market, searchindex: this.model.searchindex, query: this.model.query } ]); });
        // this.router.navigate(['/marketproduct', { market: this.model.market, searchindex: this.model.searchindex, query: this.model.query } ]);
    }

    // TODO: Remove this when we're done
    get diagnostic() { return JSON.stringify(this.model); }

    newSearch() {
        this.model = new Formsearch(this.markets[0], this.searchindexes[0], '', '1');
    }

}
