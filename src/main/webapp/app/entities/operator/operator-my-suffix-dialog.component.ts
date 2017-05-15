import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { OperatorMySuffix } from './operator-my-suffix.model';
import { OperatorMySuffixPopupService } from './operator-my-suffix-popup.service';
import { OperatorMySuffixService } from './operator-my-suffix.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-operator-my-suffix-dialog',
    templateUrl: './operator-my-suffix-dialog.component.html'
})
export class OperatorMySuffixDialogComponent implements OnInit {

    operator: OperatorMySuffix;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private operatorService: OperatorMySuffixService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operator.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operatorService.update(this.operator));
        } else {
            this.subscribeToSaveResponse(
                this.operatorService.create(this.operator));
        }
    }

    private subscribeToSaveResponse(result: Observable<OperatorMySuffix>) {
        result.subscribe((res: OperatorMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: OperatorMySuffix) {
        this.eventManager.broadcast({ name: 'operatorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-operator-my-suffix-popup',
    template: ''
})
export class OperatorMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operatorPopupService: OperatorMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.operatorPopupService
                    .open(OperatorMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.operatorPopupService
                    .open(OperatorMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
