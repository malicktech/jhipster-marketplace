/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketProductAttributesDetailComponent } from '../../../../../../main/webapp/app/entities/market-product-attributes/market-product-attributes-detail.component';
import { MarketProductAttributesService } from '../../../../../../main/webapp/app/entities/market-product-attributes/market-product-attributes.service';
import { MarketProductAttributes } from '../../../../../../main/webapp/app/entities/market-product-attributes/market-product-attributes.model';

describe('Component Tests', () => {

    describe('MarketProductAttributes Management Detail Component', () => {
        let comp: MarketProductAttributesDetailComponent;
        let fixture: ComponentFixture<MarketProductAttributesDetailComponent>;
        let service: MarketProductAttributesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [MarketProductAttributesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketProductAttributesService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketProductAttributesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketProductAttributesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketProductAttributesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketProductAttributes(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketProductAttributes).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
