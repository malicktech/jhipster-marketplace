import { BaseEntity } from './../../shared';

export class MarketProduct implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public price?: number,
        public category?: string,
        public imgContentType?: string,
        public img?: any,
        public marketOrderlineId?: number,
        public attributes?: BaseEntity[],
        public categoryId?: number,
    ) {
    }
}
