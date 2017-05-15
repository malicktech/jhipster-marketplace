
const enum Gender {
    'MALE',
    'FEMALE'

};
export class OperatorMySuffix {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public hireDate?: any,
        public gender?: Gender,
        public userId?: number,
    ) {
    }
}
