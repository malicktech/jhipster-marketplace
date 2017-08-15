import { BaseEntity } from './../../shared';

const enum OrderStatus {
    'PAID',
    'VALIDATED',
    'SHIPPED'
}

export class MarketOrders implements BaseEntity {
    constructor(
        public id?: number,
        public orderDate?: any,
        public shipdate?: any,
        public totalPrice?: number,
        public discount?: string,
        public weight?: number,
        public trackingNumber?: string,
        public ordertatus?: OrderStatus,
        public remoteVirtualCardId?: string,
        public marketOrderId?: string,
        public paymentId?: number,
        public shipperId?: number,
        public quantities?: BaseEntity[],
        public customerId?: number,
    ) {
    }
}
