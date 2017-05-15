import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { OperatorMySuffix } from './operator-my-suffix.model';
import { OperatorMySuffixPopupService } from './operator-my-suffix-popup.service';
import { OperatorMySuffixService } from './operator-my-suffix.service';

@Component({
    selector: 'jhi-operator-my-suffix-delete-dialog',
    templateUrl: './operator-my-suffix-delete-dialog.component.html'
})
export class OperatorMySuffixDeleteDialogComponent {

    operator: OperatorMySuffix;

    constructor(
        private operatorService: OperatorMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operatorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'operatorListModification',
                content: 'Deleted an operator'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-operator-my-suffix-delete-popup',
    template: ''
})
export class OperatorMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operatorPopupService: OperatorMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.operatorPopupService
                .open(OperatorMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
