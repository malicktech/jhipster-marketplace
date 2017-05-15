
const enum Status {
    'PAYED',
    'VALIDATED',
    'SHIPPED'

};
export class MarketOrderMySuffix {
    constructor(
        public id?: number,
        public orderDate?: any,
        public date?: any,
        public status?: Status,
        public remoteVirtualCardId?: string,
        public marketOrderId?: string,
        public paymentId?: number,
        public itemsId?: number,
        public quantityId?: number,
        public customerId?: number,
    ) {
    }
}
