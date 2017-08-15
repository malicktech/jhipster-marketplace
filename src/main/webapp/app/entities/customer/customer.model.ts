import { BaseEntity } from './../../shared';

const enum Gender {
    'MALE',
    'FEMALE'
}

export class Customer implements BaseEntity {
    constructor(
        public id?: number,
        public phoneNumber?: string,
        public gender?: Gender,
        public dateOfBirth?: any,
        public userId?: number,
        public addresses?: BaseEntity[],
        public orders?: BaseEntity[],
    ) {
    }
}
