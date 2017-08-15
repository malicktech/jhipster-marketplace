/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerAddressDetailComponent } from '../../../../../../main/webapp/app/entities/customer-address/customer-address-detail.component';
import { CustomerAddressService } from '../../../../../../main/webapp/app/entities/customer-address/customer-address.service';
import { CustomerAddress } from '../../../../../../main/webapp/app/entities/customer-address/customer-address.model';

describe('Component Tests', () => {

    describe('CustomerAddress Management Detail Component', () => {
        let comp: CustomerAddressDetailComponent;
        let fixture: ComponentFixture<CustomerAddressDetailComponent>;
        let service: CustomerAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [CustomerAddressDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerAddressService,
                    JhiEventManager
                ]
            }).overrideTemplate(CustomerAddressDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerAddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerAddress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerAddress).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
