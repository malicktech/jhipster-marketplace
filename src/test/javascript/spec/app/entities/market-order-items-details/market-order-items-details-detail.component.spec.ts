/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketOrderItemsDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/market-order-items-details/market-order-items-details-detail.component';
import { MarketOrderItemsDetailsService } from '../../../../../../main/webapp/app/entities/market-order-items-details/market-order-items-details.service';
import { MarketOrderItemsDetails } from '../../../../../../main/webapp/app/entities/market-order-items-details/market-order-items-details.model';

describe('Component Tests', () => {

    describe('MarketOrderItemsDetails Management Detail Component', () => {
        let comp: MarketOrderItemsDetailsDetailComponent;
        let fixture: ComponentFixture<MarketOrderItemsDetailsDetailComponent>;
        let service: MarketOrderItemsDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketOrderItemsDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketOrderItemsDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketOrderItemsDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOrderItemsDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOrderItemsDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketOrderItemsDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketOrderItemsDetails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
