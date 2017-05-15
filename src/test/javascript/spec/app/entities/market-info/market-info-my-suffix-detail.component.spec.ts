import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MarketplacejhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketInfoMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/market-info/market-info-my-suffix-detail.component';
import { MarketInfoMySuffixService } from '../../../../../../main/webapp/app/entities/market-info/market-info-my-suffix.service';
import { MarketInfoMySuffix } from '../../../../../../main/webapp/app/entities/market-info/market-info-my-suffix.model';

describe('Component Tests', () => {

    describe('MarketInfoMySuffix Management Detail Component', () => {
        let comp: MarketInfoMySuffixDetailComponent;
        let fixture: ComponentFixture<MarketInfoMySuffixDetailComponent>;
        let service: MarketInfoMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketplacejhipsterTestModule],
                declarations: [MarketInfoMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketInfoMySuffixService,
                    EventManager
                ]
            }).overrideComponent(MarketInfoMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketInfoMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketInfoMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketInfoMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketInfo).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
