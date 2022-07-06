import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUserPaymentMySuffix, UserPaymentMySuffix } from '../user-payment-my-suffix.model';

import { UserPaymentMySuffixService } from './user-payment-my-suffix.service';

describe('UserPaymentMySuffix Service', () => {
  let service: UserPaymentMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserPaymentMySuffix;
  let expectedResult: IUserPaymentMySuffix | IUserPaymentMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserPaymentMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      payAmount: 0,
      balance: 0,
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

    it('should create a UserPaymentMySuffix', () => {
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

      service.create(new UserPaymentMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserPaymentMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          payAmount: 1,
          balance: 1,
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

    it('should partial update a UserPaymentMySuffix', () => {
      const patchObject = Object.assign(
        {
          payAmount: 1,
          balance: 1,
          endDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new UserPaymentMySuffix()
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

    it('should return a list of UserPaymentMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          payAmount: 1,
          balance: 1,
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

    it('should delete a UserPaymentMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserPaymentMySuffixToCollectionIfMissing', () => {
      it('should add a UserPaymentMySuffix to an empty array', () => {
        const userPayment: IUserPaymentMySuffix = { id: 123 };
        expectedResult = service.addUserPaymentMySuffixToCollectionIfMissing([], userPayment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userPayment);
      });

      it('should not add a UserPaymentMySuffix to an array that contains it', () => {
        const userPayment: IUserPaymentMySuffix = { id: 123 };
        const userPaymentCollection: IUserPaymentMySuffix[] = [
          {
            ...userPayment,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserPaymentMySuffixToCollectionIfMissing(userPaymentCollection, userPayment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserPaymentMySuffix to an array that doesn't contain it", () => {
        const userPayment: IUserPaymentMySuffix = { id: 123 };
        const userPaymentCollection: IUserPaymentMySuffix[] = [{ id: 456 }];
        expectedResult = service.addUserPaymentMySuffixToCollectionIfMissing(userPaymentCollection, userPayment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userPayment);
      });

      it('should add only unique UserPaymentMySuffix to an array', () => {
        const userPaymentArray: IUserPaymentMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 66305 }];
        const userPaymentCollection: IUserPaymentMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserPaymentMySuffixToCollectionIfMissing(userPaymentCollection, ...userPaymentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userPayment: IUserPaymentMySuffix = { id: 123 };
        const userPayment2: IUserPaymentMySuffix = { id: 456 };
        expectedResult = service.addUserPaymentMySuffixToCollectionIfMissing([], userPayment, userPayment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userPayment);
        expect(expectedResult).toContain(userPayment2);
      });

      it('should accept null and undefined values', () => {
        const userPayment: IUserPaymentMySuffix = { id: 123 };
        expectedResult = service.addUserPaymentMySuffixToCollectionIfMissing([], null, userPayment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userPayment);
      });

      it('should return initial array if no UserPaymentMySuffix is added', () => {
        const userPaymentCollection: IUserPaymentMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserPaymentMySuffixToCollectionIfMissing(userPaymentCollection, undefined, null);
        expect(expectedResult).toEqual(userPaymentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
