import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MarketplacejhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketOrderItemsMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/market-order-items/market-order-items-my-suffix-detail.component';
import { MarketOrderItemsMySuffixService } from '../../../../../../main/webapp/app/entities/market-order-items/market-order-items-my-suffix.service';
import { MarketOrderItemsMySuffix } from '../../../../../../main/webapp/app/entities/market-order-items/market-order-items-my-suffix.model';

describe('Component Tests', () => {

    describe('MarketOrderItemsMySuffix Management Detail Component', () => {
        let comp: MarketOrderItemsMySuffixDetailComponent;
        let fixture: ComponentFixture<MarketOrderItemsMySuffixDetailComponent>;
        let service: MarketOrderItemsMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketplacejhipsterTestModule],
                declarations: [MarketOrderItemsMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketOrderItemsMySuffixService,
                    EventManager
                ]
            }).overrideComponent(MarketOrderItemsMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOrderItemsMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOrderItemsMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketOrderItemsMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketOrderItems).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
