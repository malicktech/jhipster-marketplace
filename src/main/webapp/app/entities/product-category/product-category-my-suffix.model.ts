
const enum CategoryStatus {
    'AVAILABLE',
    'RESTRICTED',
    'DISABLED'

};
export class ProductCategoryMySuffix {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public activated?: boolean,
        public defaultSortOrder?: string,
        public status?: CategoryStatus,
        public marketId?: number,
    ) {
        this.activated = false;
    }
}
