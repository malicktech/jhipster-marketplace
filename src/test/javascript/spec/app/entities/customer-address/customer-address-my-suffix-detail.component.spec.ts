import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MarketplacejhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerAddressMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/customer-address/customer-address-my-suffix-detail.component';
import { CustomerAddressMySuffixService } from '../../../../../../main/webapp/app/entities/customer-address/customer-address-my-suffix.service';
import { CustomerAddressMySuffix } from '../../../../../../main/webapp/app/entities/customer-address/customer-address-my-suffix.model';

describe('Component Tests', () => {

    describe('CustomerAddressMySuffix Management Detail Component', () => {
        let comp: CustomerAddressMySuffixDetailComponent;
        let fixture: ComponentFixture<CustomerAddressMySuffixDetailComponent>;
        let service: CustomerAddressMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketplacejhipsterTestModule],
                declarations: [CustomerAddressMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerAddressMySuffixService,
                    EventManager
                ]
            }).overrideComponent(CustomerAddressMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerAddressMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerAddressMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerAddressMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerAddress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
