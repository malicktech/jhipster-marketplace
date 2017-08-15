/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketOrderlineDetailComponent } from '../../../../../../main/webapp/app/entities/market-orderline/market-orderline-detail.component';
import { MarketOrderlineService } from '../../../../../../main/webapp/app/entities/market-orderline/market-orderline.service';
import { MarketOrderline } from '../../../../../../main/webapp/app/entities/market-orderline/market-orderline.model';

describe('Component Tests', () => {

    describe('MarketOrderline Management Detail Component', () => {
        let comp: MarketOrderlineDetailComponent;
        let fixture: ComponentFixture<MarketOrderlineDetailComponent>;
        let service: MarketOrderlineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketOrderlineDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketOrderlineService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketOrderlineDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOrderlineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOrderlineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketOrderline(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketOrderline).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
