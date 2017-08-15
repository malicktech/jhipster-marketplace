import { BaseEntity } from './../../shared';

const enum PaymentType {
    'CREDITCARD',
    'MOBILE',
    'ONLINESYSTEM'
}

export class Payment implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public amount?: number,
        public allowed?: boolean,
        public paymentType?: PaymentType,
        public orderId?: number,
    ) {
        this.allowed = false;
    }
}
