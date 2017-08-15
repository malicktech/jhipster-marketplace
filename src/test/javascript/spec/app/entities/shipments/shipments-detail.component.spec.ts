/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShipmentsDetailComponent } from '../../../../../../main/webapp/app/entities/shipments/shipments-detail.component';
import { ShipmentsService } from '../../../../../../main/webapp/app/entities/shipments/shipments.service';
import { Shipments } from '../../../../../../main/webapp/app/entities/shipments/shipments.model';

describe('Component Tests', () => {

    describe('Shipments Management Detail Component', () => {
        let comp: ShipmentsDetailComponent;
        let fixture: ComponentFixture<ShipmentsDetailComponent>;
        let service: ShipmentsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [ShipmentsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShipmentsService,
                    JhiEventManager
                ]
            }).overrideTemplate(ShipmentsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShipmentsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipmentsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Shipments(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shipments).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
