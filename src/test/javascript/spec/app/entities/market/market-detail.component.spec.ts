/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketDetailComponent } from '../../../../../../main/webapp/app/entities/market/market-detail.component';
import { MarketService } from '../../../../../../main/webapp/app/entities/market/market.service';
import { Market } from '../../../../../../main/webapp/app/entities/market/market.model';

describe('Component Tests', () => {

    describe('Market Management Detail Component', () => {
        let comp: MarketDetailComponent;
        let fixture: ComponentFixture<MarketDetailComponent>;
        let service: MarketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Market(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.market).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
