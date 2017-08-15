/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MarketTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InvoicesDetailComponent } from '../../../../../../main/webapp/app/entities/invoices/invoices-detail.component';
import { InvoicesService } from '../../../../../../main/webapp/app/entities/invoices/invoices.service';
import { Invoices } from '../../../../../../main/webapp/app/entities/invoices/invoices.model';

describe('Component Tests', () => {

    describe('Invoices Management Detail Component', () => {
        let comp: InvoicesDetailComponent;
        let fixture: ComponentFixture<InvoicesDetailComponent>;
        let service: InvoicesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketTestModule],
                declarations: [InvoicesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InvoicesService,
                    JhiEventManager
                ]
            }).overrideTemplate(InvoicesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoicesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoicesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Invoices(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.invoices).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
