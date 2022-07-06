import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { UserPaymentType } from 'app/entities/enumerations/user-payment-type.model';
import { IUserPaymentConfMySuffix, UserPaymentConfMySuffix } from '../user-payment-conf-my-suffix.model';

import { UserPaymentConfMySuffixService } from './user-payment-conf-my-suffix.service';

describe('UserPaymentConfMySuffix Service', () => {
  let service: UserPaymentConfMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserPaymentConfMySuffix;
  let expectedResult: IUserPaymentConfMySuffix | IUserPaymentConfMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserPaymentConfMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      type: UserPaymentType.FIX,
      total: 0,
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

    it('should create a UserPaymentConfMySuffix', () => {
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

      service.create(new UserPaymentConfMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserPaymentConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          total: 1,
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

    it('should partial update a UserPaymentConfMySuffix', () => {
      const patchObject = Object.assign({}, new UserPaymentConfMySuffix());

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

    it('should return a list of UserPaymentConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          total: 1,
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

    it('should delete a UserPaymentConfMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserPaymentConfMySuffixToCollectionIfMissing', () => {
      it('should add a UserPaymentConfMySuffix to an empty array', () => {
        const userPaymentConf: IUserPaymentConfMySuffix = { id: 123 };
        expectedResult = service.addUserPaymentConfMySuffixToCollectionIfMissing([], userPaymentConf);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userPaymentConf);
      });

      it('should not add a UserPaymentConfMySuffix to an array that contains it', () => {
        const userPaymentConf: IUserPaymentConfMySuffix = { id: 123 };
        const userPaymentConfCollection: IUserPaymentConfMySuffix[] = [
          {
            ...userPaymentConf,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserPaymentConfMySuffixToCollectionIfMissing(userPaymentConfCollection, userPaymentConf);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserPaymentConfMySuffix to an array that doesn't contain it", () => {
        const userPaymentConf: IUserPaymentConfMySuffix = { id: 123 };
        const userPaymentConfCollection: IUserPaymentConfMySuffix[] = [{ id: 456 }];
        expectedResult = service.addUserPaymentConfMySuffixToCollectionIfMissing(userPaymentConfCollection, userPaymentConf);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userPaymentConf);
      });

      it('should add only unique UserPaymentConfMySuffix to an array', () => {
        const userPaymentConfArray: IUserPaymentConfMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 71057 }];
        const userPaymentConfCollection: IUserPaymentConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserPaymentConfMySuffixToCollectionIfMissing(userPaymentConfCollection, ...userPaymentConfArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userPaymentConf: IUserPaymentConfMySuffix = { id: 123 };
        const userPaymentConf2: IUserPaymentConfMySuffix = { id: 456 };
        expectedResult = service.addUserPaymentConfMySuffixToCollectionIfMissing([], userPaymentConf, userPaymentConf2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userPaymentConf);
        expect(expectedResult).toContain(userPaymentConf2);
      });

      it('should accept null and undefined values', () => {
        const userPaymentConf: IUserPaymentConfMySuffix = { id: 123 };
        expectedResult = service.addUserPaymentConfMySuffixToCollectionIfMissing([], null, userPaymentConf, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userPaymentConf);
      });

      it('should return initial array if no UserPaymentConfMySuffix is added', () => {
        const userPaymentConfCollection: IUserPaymentConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserPaymentConfMySuffixToCollectionIfMissing(userPaymentConfCollection, undefined, null);
        expect(expectedResult).toEqual(userPaymentConfCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
