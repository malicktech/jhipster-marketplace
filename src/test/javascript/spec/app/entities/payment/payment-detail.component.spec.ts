/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaymentDetailComponent } from '../../../../../../main/webapp/app/entities/payment/payment-detail.component';
import { PaymentService } from '../../../../../../main/webapp/app/entities/payment/payment.service';
import { Payment } from '../../../../../../main/webapp/app/entities/payment/payment.model';

describe('Component Tests', () => {

    describe('Payment Management Detail Component', () => {
        let comp: PaymentDetailComponent;
        let fixture: ComponentFixture<PaymentDetailComponent>;
        let service: PaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [PaymentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaymentService,
                    JhiEventManager
                ]
            }).overrideTemplate(PaymentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Payment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.payment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
