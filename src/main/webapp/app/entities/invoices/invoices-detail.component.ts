import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Invoices } from './invoices.model';
import { InvoicesService } from './invoices.service';

@Component({
    selector: 'jhi-invoices-detail',
    templateUrl: './invoices-detail.component.html'
})
export class InvoicesDetailComponent implements OnInit, OnDestroy {

    invoices: Invoices;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private invoicesService: InvoicesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInvoices();
    }

    load(id) {
        this.invoicesService.find(id).subscribe((invoices) => {
            this.invoices = invoices;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInvoices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'invoicesListModification',
            (response) => this.load(this.invoices.id)
        );
    }
}
