import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketInfoMySuffix } from './market-info-my-suffix.model';
import { MarketInfoMySuffixService } from './market-info-my-suffix.service';
@Injectable()
export class MarketInfoMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketInfoService: MarketInfoMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketInfoService.find(id).subscribe((marketInfo) => {
                this.marketInfoModalRef(component, marketInfo);
            });
        } else {
            return this.marketInfoModalRef(component, new MarketInfoMySuffix());
        }
    }

    marketInfoModalRef(component: Component, marketInfo: MarketInfoMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketInfo = marketInfo;
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
