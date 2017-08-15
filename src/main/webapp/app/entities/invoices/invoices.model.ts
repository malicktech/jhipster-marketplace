import { BaseEntity } from './../../shared';

export class Invoices implements BaseEntity {
    constructor(
        public id?: number,
        public invoiceDate?: any,
        public invoiceDetails?: string,
    ) {
    }
}
