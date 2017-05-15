import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { OperatorMySuffix } from './operator-my-suffix.model';
import { OperatorMySuffixService } from './operator-my-suffix.service';
@Injectable()
export class OperatorMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private operatorService: OperatorMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.operatorService.find(id).subscribe((operator) => {
                operator.hireDate = this.datePipe
                    .transform(operator.hireDate, 'yyyy-MM-ddThh:mm');
                this.operatorModalRef(component, operator);
            });
        } else {
            return this.operatorModalRef(component, new OperatorMySuffix());
        }
    }

    operatorModalRef(component: Component, operator: OperatorMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.operator = operator;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
