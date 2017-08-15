import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Shipments } from './shipments.model';
import { ShipmentsService } from './shipments.service';

@Component({
    selector: 'jhi-shipments-detail',
    templateUrl: './shipments-detail.component.html'
})
export class ShipmentsDetailComponent implements OnInit, OnDestroy {

    shipments: Shipments;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shipmentsService: ShipmentsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShipments();
    }

    load(id) {
        this.shipmentsService.find(id).subscribe((shipments) => {
            this.shipments = shipments;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShipments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shipmentsListModification',
            (response) => this.load(this.shipments.id)
        );
    }
}
