/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketProductDetailComponent } from '../../../../../../main/webapp/app/entities/market-product/market-product-detail.component';
import { MarketProductService } from '../../../../../../main/webapp/app/entities/market-product/market-product.service';
import { MarketProduct } from '../../../../../../main/webapp/app/entities/market-product/market-product.model';

describe('Component Tests', () => {

    describe('MarketProduct Management Detail Component', () => {
        let comp: MarketProductDetailComponent;
        let fixture: ComponentFixture<MarketProductDetailComponent>;
        let service: MarketProductService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketProductDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketProductService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketProductDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketProductDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketProductService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketProduct(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketProduct).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
