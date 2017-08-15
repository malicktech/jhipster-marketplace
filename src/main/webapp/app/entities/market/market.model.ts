import { BaseEntity } from './../../shared';

export class Market implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public content?: any,
        public infos?: BaseEntity[],
        public categories?: BaseEntity[],
    ) {
    }
}
