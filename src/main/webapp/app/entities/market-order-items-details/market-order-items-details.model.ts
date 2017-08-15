import { BaseEntity } from './../../shared';

export class MarketOrderItemsDetails implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public marketOrderlineId?: number,
    ) {
    }
}
