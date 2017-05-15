import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { MarketOrderMySuffix } from './market-order-my-suffix.model';
import { MarketOrderMySuffixService } from './market-order-my-suffix.service';
@Injectable()
export class MarketOrderMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private marketOrderService: MarketOrderMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketOrderService.find(id).subscribe((marketOrder) => {
                if (marketOrder.orderDate) {
                    marketOrder.orderDate = {
                        year: marketOrder.orderDate.getFullYear(),
                        month: marketOrder.orderDate.getMonth() + 1,
                        day: marketOrder.orderDate.getDate()
                    };
                }
                marketOrder.date = this.datePipe
                    .transform(marketOrder.date, 'yyyy-MM-ddThh:mm');
                this.marketOrderModalRef(component, marketOrder);
            });
        } else {
            return this.marketOrderModalRef(component, new MarketOrderMySuffix());
        }
    }

    marketOrderModalRef(component: Component, marketOrder: MarketOrderMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketOrder = marketOrder;
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
