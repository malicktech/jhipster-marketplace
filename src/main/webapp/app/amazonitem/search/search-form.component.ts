import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Formsearch } from './search-form';
import { ProductService } from '../marketproduct/product.service';

@Component({
    selector: 'jhi-search-form',
    templateUrl: './search-form.component.html',
    styleUrls: [],
})
export class SearchFormComponent implements OnInit {

    markets: any;
    searchindexes: any;
    success: boolean;

    model: any;

    submitted: boolean;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private productService: ProductService
    ) { }

    ngOnInit() {
        this.success = false;
        this.submitted = true;
        this.markets = ['amazon', 'cdiscount'];
        this.searchindexes = ['All', 'Books', 'Electronics', 'PCHardware', 'Apparel'];
        this.model = new Formsearch(this.markets[0], this.searchindexes[0], 'java', '1');
    }

    onSubmit() {
        this.submitted = true;
        this.success = true;
        // console.log(this.model);
        // this.router.navigate(['/']);
        // this.router.navigateByUrl('/register');

        // this.productService.getProducts(this.model.market, this.model.searchindex, this.model.query, '1')
        // .then((products) => { 
        // this.router.navigate(['/']);
        // this.router.navigate(['/marketproduct', { market: this.model.market, searchindex: this.model.searchindex, query: this.model.query } ]); 
        // });

        let link:any[] = ['/marketproduct', { query: this.model.query, market: this.model.market, searchindex: this.model.searchindex}];
        this.router.navigate(link)
            .then((nav) => {
                console.log(nav); // true if navigation is successful
            }, err => {
                console.log(err) // when there's an error
            });

        // https://angular.io/api/router/Router#navigateByUrl
        // let tmp = 
        // const tmp = `/marketproduct?market=${this.model.market}&searchindex=${this.model.searchindex}&query=${this.model.query}`;
        // this.router.navigateByUrl(tmp, { skipLocationChange: true });

    }

    // TODO: Remove this when we're done
    get diagnostic() { return JSON.stringify(this.model); }

    newSearch() {
        this.model = new Formsearch(this.markets[0], this.searchindexes[0], '', '1');
    }

}
