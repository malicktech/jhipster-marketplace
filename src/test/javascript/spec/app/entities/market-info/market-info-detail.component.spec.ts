/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketInfoDetailComponent } from '../../../../../../main/webapp/app/entities/market-info/market-info-detail.component';
import { MarketInfoService } from '../../../../../../main/webapp/app/entities/market-info/market-info.service';
import { MarketInfo } from '../../../../../../main/webapp/app/entities/market-info/market-info.model';

describe('Component Tests', () => {

    describe('MarketInfo Management Detail Component', () => {
        let comp: MarketInfoDetailComponent;
        let fixture: ComponentFixture<MarketInfoDetailComponent>;
        let service: MarketInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketInfoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketInfoService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketInfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketInfo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
