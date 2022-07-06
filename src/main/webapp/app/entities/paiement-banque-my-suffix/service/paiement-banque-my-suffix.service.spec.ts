import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPaiementBanqueMySuffix, PaiementBanqueMySuffix } from '../paiement-banque-my-suffix.model';

import { PaiementBanqueMySuffixService } from './paiement-banque-my-suffix.service';

describe('PaiementBanqueMySuffix Service', () => {
  let service: PaiementBanqueMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaiementBanqueMySuffix;
  let expectedResult: IPaiementBanqueMySuffix | IPaiementBanqueMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaiementBanqueMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      montant: 0,
      balance: 0,
      description: 'AAAAAAA',
      startDate: currentDate,
      endDate: currentDate,
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
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
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

    it('should create a PaiementBanqueMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new PaiementBanqueMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaiementBanqueMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          montant: 1,
          balance: 1,
          description: 'BBBBBB',
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
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

    it('should partial update a PaiementBanqueMySuffix', () => {
      const patchObject = Object.assign(
        {
          balance: 1,
          description: 'BBBBBB',
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new PaiementBanqueMySuffix()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
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

    it('should return a list of PaiementBanqueMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          montant: 1,
          balance: 1,
          description: 'BBBBBB',
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
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

    it('should delete a PaiementBanqueMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaiementBanqueMySuffixToCollectionIfMissing', () => {
      it('should add a PaiementBanqueMySuffix to an empty array', () => {
        const paiementBanque: IPaiementBanqueMySuffix = { id: 123 };
        expectedResult = service.addPaiementBanqueMySuffixToCollectionIfMissing([], paiementBanque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paiementBanque);
      });

      it('should not add a PaiementBanqueMySuffix to an array that contains it', () => {
        const paiementBanque: IPaiementBanqueMySuffix = { id: 123 };
        const paiementBanqueCollection: IPaiementBanqueMySuffix[] = [
          {
            ...paiementBanque,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaiementBanqueMySuffixToCollectionIfMissing(paiementBanqueCollection, paiementBanque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaiementBanqueMySuffix to an array that doesn't contain it", () => {
        const paiementBanque: IPaiementBanqueMySuffix = { id: 123 };
        const paiementBanqueCollection: IPaiementBanqueMySuffix[] = [{ id: 456 }];
        expectedResult = service.addPaiementBanqueMySuffixToCollectionIfMissing(paiementBanqueCollection, paiementBanque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paiementBanque);
      });

      it('should add only unique PaiementBanqueMySuffix to an array', () => {
        const paiementBanqueArray: IPaiementBanqueMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 29393 }];
        const paiementBanqueCollection: IPaiementBanqueMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPaiementBanqueMySuffixToCollectionIfMissing(paiementBanqueCollection, ...paiementBanqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paiementBanque: IPaiementBanqueMySuffix = { id: 123 };
        const paiementBanque2: IPaiementBanqueMySuffix = { id: 456 };
        expectedResult = service.addPaiementBanqueMySuffixToCollectionIfMissing([], paiementBanque, paiementBanque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paiementBanque);
        expect(expectedResult).toContain(paiementBanque2);
      });

      it('should accept null and undefined values', () => {
        const paiementBanque: IPaiementBanqueMySuffix = { id: 123 };
        expectedResult = service.addPaiementBanqueMySuffixToCollectionIfMissing([], null, paiementBanque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paiementBanque);
      });

      it('should return initial array if no PaiementBanqueMySuffix is added', () => {
        const paiementBanqueCollection: IPaiementBanqueMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPaiementBanqueMySuffixToCollectionIfMissing(paiementBanqueCollection, undefined, null);
        expect(expectedResult).toEqual(paiementBanqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
