import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TirageMySuffixDetailComponent } from './tirage-my-suffix-detail.component';

describe('TirageMySuffix Management Detail Component', () => {
  let comp: TirageMySuffixDetailComponent;
  let fixture: ComponentFixture<TirageMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TirageMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tirage: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TirageMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TirageMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tirage on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tirage).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
