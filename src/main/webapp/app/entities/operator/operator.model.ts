import { BaseEntity } from './../../shared';

const enum Gender {
    'MALE',
    'FEMALE'
}

export class Operator implements BaseEntity {
    constructor(
        public id?: number,
        public phoneNumber?: string,
        public gender?: Gender,
        public hireDate?: any,
        public userId?: number,
    ) {
    }
}
