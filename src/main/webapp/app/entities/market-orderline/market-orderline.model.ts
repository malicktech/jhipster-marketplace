import { BaseEntity } from './../../shared';

export class MarketOrderline implements BaseEntity {
    constructor(
        public id?: number,
        public quantity?: number,
        public marketOrdersId?: number,
        public details?: BaseEntity[],
    ) {
    }
}
