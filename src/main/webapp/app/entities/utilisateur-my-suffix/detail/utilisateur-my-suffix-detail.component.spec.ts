import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UtilisateurMySuffixDetailComponent } from './utilisateur-my-suffix-detail.component';

describe('UtilisateurMySuffix Management Detail Component', () => {
  let comp: UtilisateurMySuffixDetailComponent;
  let fixture: ComponentFixture<UtilisateurMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UtilisateurMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ utilisateur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UtilisateurMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UtilisateurMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load utilisateur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.utilisateur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
