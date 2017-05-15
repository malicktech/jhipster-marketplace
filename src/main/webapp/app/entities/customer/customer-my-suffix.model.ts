
const enum Gender {
    'MALE',
    'FEMALE'

};
export class CustomerMySuffix {
    constructor(
        public id?: number,
        public telephone?: string,
        public gender?: Gender,
        public dateOfBirth?: any,
        public userId?: number,
        public addressId?: number,
        public orderId?: number,
    ) {
    }
}
