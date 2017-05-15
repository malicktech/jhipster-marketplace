import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MarketplacejhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/market/market-my-suffix-detail.component';
import { MarketMySuffixService } from '../../../../../../main/webapp/app/entities/market/market-my-suffix.service';
import { MarketMySuffix } from '../../../../../../main/webapp/app/entities/market/market-my-suffix.model';

describe('Component Tests', () => {

    describe('MarketMySuffix Management Detail Component', () => {
        let comp: MarketMySuffixDetailComponent;
        let fixture: ComponentFixture<MarketMySuffixDetailComponent>;
        let service: MarketMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketplacejhipsterTestModule],
                declarations: [MarketMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketMySuffixService,
                    EventManager
                ]
            }).overrideComponent(MarketMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.market).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
