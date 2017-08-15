import { BaseEntity } from './../../shared';

export class MarketProductAttributes implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public marketProductId?: number,
    ) {
    }
}
