import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { OperatorMySuffix } from './operator-my-suffix.model';
import { OperatorMySuffixService } from './operator-my-suffix.service';

@Component({
    selector: 'jhi-operator-my-suffix-detail',
    templateUrl: './operator-my-suffix-detail.component.html'
})
export class OperatorMySuffixDetailComponent implements OnInit, OnDestroy {

    operator: OperatorMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private operatorService: OperatorMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOperators();
    }

    load(id) {
        this.operatorService.find(id).subscribe((operator) => {
            this.operator = operator;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOperators() {
        this.eventSubscriber = this.eventManager.subscribe(
            'operatorListModification',
            (response) => this.load(this.operator.id)
        );
    }
}
