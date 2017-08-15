/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketOrdersDetailComponent } from '../../../../../../main/webapp/app/entities/market-orders/market-orders-detail.component';
import { MarketOrdersService } from '../../../../../../main/webapp/app/entities/market-orders/market-orders.service';
import { MarketOrders } from '../../../../../../main/webapp/app/entities/market-orders/market-orders.model';

describe('Component Tests', () => {

    describe('MarketOrders Management Detail Component', () => {
        let comp: MarketOrdersDetailComponent;
        let fixture: ComponentFixture<MarketOrdersDetailComponent>;
        let service: MarketOrdersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketOrdersDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketOrdersService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketOrdersDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOrdersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOrdersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketOrders(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketOrders).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
