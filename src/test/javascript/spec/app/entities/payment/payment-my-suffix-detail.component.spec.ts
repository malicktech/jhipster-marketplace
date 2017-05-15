import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MarketplacejhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaymentMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/payment/payment-my-suffix-detail.component';
import { PaymentMySuffixService } from '../../../../../../main/webapp/app/entities/payment/payment-my-suffix.service';
import { PaymentMySuffix } from '../../../../../../main/webapp/app/entities/payment/payment-my-suffix.model';

describe('Component Tests', () => {

    describe('PaymentMySuffix Management Detail Component', () => {
        let comp: PaymentMySuffixDetailComponent;
        let fixture: ComponentFixture<PaymentMySuffixDetailComponent>;
        let service: PaymentMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketplacejhipsterTestModule],
                declarations: [PaymentMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaymentMySuffixService,
                    EventManager
                ]
            }).overrideComponent(PaymentMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PaymentMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.payment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
