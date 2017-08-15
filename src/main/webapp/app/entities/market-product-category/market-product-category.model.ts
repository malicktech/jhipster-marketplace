import { BaseEntity } from './../../shared';

export class MarketProductCategory implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
    ) {
    }
}
