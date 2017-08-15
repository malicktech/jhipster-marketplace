import { BaseEntity } from './../../shared';

export class CustomerAddress implements BaseEntity {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public city?: string,
        public postalCode?: string,
        public stateProvince?: string,
        public countryId?: number,
        public customerId?: number,
    ) {
    }
}
