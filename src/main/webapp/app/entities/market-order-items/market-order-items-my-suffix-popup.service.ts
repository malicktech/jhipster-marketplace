import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketOrderItemsMySuffix } from './market-order-items-my-suffix.model';
import { MarketOrderItemsMySuffixService } from './market-order-items-my-suffix.service';
@Injectable()
export class MarketOrderItemsMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketOrderItemsService: MarketOrderItemsMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketOrderItemsService.find(id).subscribe((marketOrderItems) => {
                this.marketOrderItemsModalRef(component, marketOrderItems);
            });
        } else {
            return this.marketOrderItemsModalRef(component, new MarketOrderItemsMySuffix());
        }
    }

    marketOrderItemsModalRef(component: Component, marketOrderItems: MarketOrderItemsMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketOrderItems = marketOrderItems;
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
