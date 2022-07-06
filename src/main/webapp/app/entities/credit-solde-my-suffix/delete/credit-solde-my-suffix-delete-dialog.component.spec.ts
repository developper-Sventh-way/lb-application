jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CreditSoldeMySuffixService } from '../service/credit-solde-my-suffix.service';

import { CreditSoldeMySuffixDeleteDialogComponent } from './credit-solde-my-suffix-delete-dialog.component';

describe('CreditSoldeMySuffix Management Delete Component', () => {
  let comp: CreditSoldeMySuffixDeleteDialogComponent;
  let fixture: ComponentFixture<CreditSoldeMySuffixDeleteDialogComponent>;
  let service: CreditSoldeMySuffixService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CreditSoldeMySuffixDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(CreditSoldeMySuffixDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CreditSoldeMySuffixDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CreditSoldeMySuffixService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
