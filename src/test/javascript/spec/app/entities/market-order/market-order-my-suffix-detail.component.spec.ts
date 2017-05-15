import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MarketplacejhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketOrderMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/market-order/market-order-my-suffix-detail.component';
import { MarketOrderMySuffixService } from '../../../../../../main/webapp/app/entities/market-order/market-order-my-suffix.service';
import { MarketOrderMySuffix } from '../../../../../../main/webapp/app/entities/market-order/market-order-my-suffix.model';

describe('Component Tests', () => {

    describe('MarketOrderMySuffix Management Detail Component', () => {
        let comp: MarketOrderMySuffixDetailComponent;
        let fixture: ComponentFixture<MarketOrderMySuffixDetailComponent>;
        let service: MarketOrderMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketplacejhipsterTestModule],
                declarations: [MarketOrderMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketOrderMySuffixService,
                    EventManager
                ]
            }).overrideComponent(MarketOrderMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOrderMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOrderMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketOrderMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketOrder).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
