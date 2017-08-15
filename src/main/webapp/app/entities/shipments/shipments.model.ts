import { BaseEntity } from './../../shared';

export class Shipments implements BaseEntity {
    constructor(
        public id?: number,
        public shipperType?: string,
        public companyName?: string,
        public phone?: string,
        public orderId?: number,
    ) {
    }
}
