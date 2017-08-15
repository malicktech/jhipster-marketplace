import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { MarketOrders } from './market-orders.model';
import { MarketOrdersService } from './market-orders.service';

@Injectable()
export class MarketOrdersPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private marketOrdersService: MarketOrdersService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.marketOrdersService.find(id).subscribe((marketOrders) => {
                    marketOrders.orderDate = this.datePipe
                        .transform(marketOrders.orderDate, 'yyyy-MM-ddThh:mm');
                    if (marketOrders.shipdate) {
                        marketOrders.shipdate = {
                            year: marketOrders.shipdate.getFullYear(),
                            month: marketOrders.shipdate.getMonth() + 1,
                            day: marketOrders.shipdate.getDate()
                        };
                    }
                    this.ngbModalRef = this.marketOrdersModalRef(component, marketOrders);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.marketOrdersModalRef(component, new MarketOrders());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    marketOrdersModalRef(component: Component, marketOrders: MarketOrders): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketOrders = marketOrders;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
