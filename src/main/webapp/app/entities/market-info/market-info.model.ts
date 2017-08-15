import { BaseEntity } from './../../shared';

export class MarketInfo implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public marketId?: number,
    ) {
    }
}
