/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketProductCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/market-product-category/market-product-category-detail.component';
import { MarketProductCategoryService } from '../../../../../../main/webapp/app/entities/market-product-category/market-product-category.service';
import { MarketProductCategory } from '../../../../../../main/webapp/app/entities/market-product-category/market-product-category.model';

describe('Component Tests', () => {

    describe('MarketProductCategory Management Detail Component', () => {
        let comp: MarketProductCategoryDetailComponent;
        let fixture: ComponentFixture<MarketProductCategoryDetailComponent>;
        let service: MarketProductCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketProductCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketProductCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketProductCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketProductCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketProductCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketProductCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketProductCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
