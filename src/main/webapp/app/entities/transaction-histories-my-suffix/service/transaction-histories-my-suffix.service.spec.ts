import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TransactionType } from 'app/entities/enumerations/transaction-type.model';
import { ITransactionHistoriesMySuffix, TransactionHistoriesMySuffix } from '../transaction-histories-my-suffix.model';

import { TransactionHistoriesMySuffixService } from './transaction-histories-my-suffix.service';

describe('TransactionHistoriesMySuffix Service', () => {
  let service: TransactionHistoriesMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransactionHistoriesMySuffix;
  let expectedResult: ITransactionHistoriesMySuffix | ITransactionHistoriesMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransactionHistoriesMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      type: TransactionType.DEPOT,
      description: 'AAAAAAA',
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

    it('should create a TransactionHistoriesMySuffix', () => {
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

      service.create(new TransactionHistoriesMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransactionHistoriesMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should partial update a TransactionHistoriesMySuffix', () => {
      const patchObject = Object.assign(
        {
          type: 'BBBBBB',
          description: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new TransactionHistoriesMySuffix()
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

    it('should return a list of TransactionHistoriesMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a TransactionHistoriesMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransactionHistoriesMySuffixToCollectionIfMissing', () => {
      it('should add a TransactionHistoriesMySuffix to an empty array', () => {
        const transactionHistories: ITransactionHistoriesMySuffix = { id: 123 };
        expectedResult = service.addTransactionHistoriesMySuffixToCollectionIfMissing([], transactionHistories);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactionHistories);
      });

      it('should not add a TransactionHistoriesMySuffix to an array that contains it', () => {
        const transactionHistories: ITransactionHistoriesMySuffix = { id: 123 };
        const transactionHistoriesCollection: ITransactionHistoriesMySuffix[] = [
          {
            ...transactionHistories,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransactionHistoriesMySuffixToCollectionIfMissing(transactionHistoriesCollection, transactionHistories);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransactionHistoriesMySuffix to an array that doesn't contain it", () => {
        const transactionHistories: ITransactionHistoriesMySuffix = { id: 123 };
        const transactionHistoriesCollection: ITransactionHistoriesMySuffix[] = [{ id: 456 }];
        expectedResult = service.addTransactionHistoriesMySuffixToCollectionIfMissing(transactionHistoriesCollection, transactionHistories);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactionHistories);
      });

      it('should add only unique TransactionHistoriesMySuffix to an array', () => {
        const transactionHistoriesArray: ITransactionHistoriesMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 30594 }];
        const transactionHistoriesCollection: ITransactionHistoriesMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTransactionHistoriesMySuffixToCollectionIfMissing(
          transactionHistoriesCollection,
          ...transactionHistoriesArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transactionHistories: ITransactionHistoriesMySuffix = { id: 123 };
        const transactionHistories2: ITransactionHistoriesMySuffix = { id: 456 };
        expectedResult = service.addTransactionHistoriesMySuffixToCollectionIfMissing([], transactionHistories, transactionHistories2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactionHistories);
        expect(expectedResult).toContain(transactionHistories2);
      });

      it('should accept null and undefined values', () => {
        const transactionHistories: ITransactionHistoriesMySuffix = { id: 123 };
        expectedResult = service.addTransactionHistoriesMySuffixToCollectionIfMissing([], null, transactionHistories, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactionHistories);
      });

      it('should return initial array if no TransactionHistoriesMySuffix is added', () => {
        const transactionHistoriesCollection: ITransactionHistoriesMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTransactionHistoriesMySuffixToCollectionIfMissing(transactionHistoriesCollection, undefined, null);
        expect(expectedResult).toEqual(transactionHistoriesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
