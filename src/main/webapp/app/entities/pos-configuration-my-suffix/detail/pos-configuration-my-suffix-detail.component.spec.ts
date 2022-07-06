import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { POSConfigurationMySuffixDetailComponent } from './pos-configuration-my-suffix-detail.component';

describe('POSConfigurationMySuffix Management Detail Component', () => {
  let comp: POSConfigurationMySuffixDetailComponent;
  let fixture: ComponentFixture<POSConfigurationMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [POSConfigurationMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pOSConfiguration: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(POSConfigurationMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(POSConfigurationMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pOSConfiguration on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pOSConfiguration).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
