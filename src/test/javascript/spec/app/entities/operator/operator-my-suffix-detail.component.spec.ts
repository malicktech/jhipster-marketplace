import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MarketplacejhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OperatorMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/operator/operator-my-suffix-detail.component';
import { OperatorMySuffixService } from '../../../../../../main/webapp/app/entities/operator/operator-my-suffix.service';
import { OperatorMySuffix } from '../../../../../../main/webapp/app/entities/operator/operator-my-suffix.model';

describe('Component Tests', () => {

    describe('OperatorMySuffix Management Detail Component', () => {
        let comp: OperatorMySuffixDetailComponent;
        let fixture: ComponentFixture<OperatorMySuffixDetailComponent>;
        let service: OperatorMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MarketplacejhipsterTestModule],
                declarations: [OperatorMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OperatorMySuffixService,
                    EventManager
                ]
            }).overrideComponent(OperatorMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperatorMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperatorMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OperatorMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.operator).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
