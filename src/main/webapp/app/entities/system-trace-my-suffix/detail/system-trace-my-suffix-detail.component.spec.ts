import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SystemTraceMySuffixDetailComponent } from './system-trace-my-suffix-detail.component';

describe('SystemTraceMySuffix Management Detail Component', () => {
  let comp: SystemTraceMySuffixDetailComponent;
  let fixture: ComponentFixture<SystemTraceMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SystemTraceMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ systemTrace: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SystemTraceMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SystemTraceMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load systemTrace on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.systemTrace).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
