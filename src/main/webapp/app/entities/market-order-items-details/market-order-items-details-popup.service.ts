import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketOrderItemsDetails } from './market-order-items-details.model';
import { MarketOrderItemsDetailsService } from './market-order-items-details.service';

@Injectable()
export class MarketOrderItemsDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketOrderItemsDetailsService: MarketOrderItemsDetailsService

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
                this.marketOrderItemsDetailsService.find(id).subscribe((marketOrderItemsDetails) => {
                    this.ngbModalRef = this.marketOrderItemsDetailsModalRef(component, marketOrderItemsDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.marketOrderItemsDetailsModalRef(component, new MarketOrderItemsDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    marketOrderItemsDetailsModalRef(component: Component, marketOrderItemsDetails: MarketOrderItemsDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketOrderItemsDetails = marketOrderItemsDetails;
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
