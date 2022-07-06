import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICreditSoldeMySuffix, CreditSoldeMySuffix } from '../credit-solde-my-suffix.model';

import { CreditSoldeMySuffixService } from './credit-solde-my-suffix.service';

describe('CreditSoldeMySuffix Service', () => {
  let service: CreditSoldeMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ICreditSoldeMySuffix;
  let expectedResult: ICreditSoldeMySuffix | ICreditSoldeMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CreditSoldeMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      montant: 0,
      createdBy: 'AAAAAAA',
      createdDate: currentDate,
      lastModifiedBy: 'AAAAAAA',
      lastModifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CreditSoldeMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new CreditSoldeMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CreditSoldeMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          montant: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CreditSoldeMySuffix', () => {
      const patchObject = Object.assign(
        {
          createdBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new CreditSoldeMySuffix()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CreditSoldeMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          montant: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CreditSoldeMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCreditSoldeMySuffixToCollectionIfMissing', () => {
      it('should add a CreditSoldeMySuffix to an empty array', () => {
        const creditSolde: ICreditSoldeMySuffix = { id: 123 };
        expectedResult = service.addCreditSoldeMySuffixToCollectionIfMissing([], creditSolde);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(creditSolde);
      });

      it('should not add a CreditSoldeMySuffix to an array that contains it', () => {
        const creditSolde: ICreditSoldeMySuffix = { id: 123 };
        const creditSoldeCollection: ICreditSoldeMySuffix[] = [
          {
            ...creditSolde,
          },
          { id: 456 },
        ];
        expectedResult = service.addCreditSoldeMySuffixToCollectionIfMissing(creditSoldeCollection, creditSolde);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CreditSoldeMySuffix to an array that doesn't contain it", () => {
        const creditSolde: ICreditSoldeMySuffix = { id: 123 };
        const creditSoldeCollection: ICreditSoldeMySuffix[] = [{ id: 456 }];
        expectedResult = service.addCreditSoldeMySuffixToCollectionIfMissing(creditSoldeCollection, creditSolde);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(creditSolde);
      });

      it('should add only unique CreditSoldeMySuffix to an array', () => {
        const creditSoldeArray: ICreditSoldeMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 63332 }];
        const creditSoldeCollection: ICreditSoldeMySuffix[] = [{ id: 123 }];
        expectedResult = service.addCreditSoldeMySuffixToCollectionIfMissing(creditSoldeCollection, ...creditSoldeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const creditSolde: ICreditSoldeMySuffix = { id: 123 };
        const creditSolde2: ICreditSoldeMySuffix = { id: 456 };
        expectedResult = service.addCreditSoldeMySuffixToCollectionIfMissing([], creditSolde, creditSolde2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(creditSolde);
        expect(expectedResult).toContain(creditSolde2);
      });

      it('should accept null and undefined values', () => {
        const creditSolde: ICreditSoldeMySuffix = { id: 123 };
        expectedResult = service.addCreditSoldeMySuffixToCollectionIfMissing([], null, creditSolde, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(creditSolde);
      });

      it('should return initial array if no CreditSoldeMySuffix is added', () => {
        const creditSoldeCollection: ICreditSoldeMySuffix[] = [{ id: 123 }];
        expectedResult = service.addCreditSoldeMySuffixToCollectionIfMissing(creditSoldeCollection, undefined, null);
        expect(expectedResult).toEqual(creditSoldeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
