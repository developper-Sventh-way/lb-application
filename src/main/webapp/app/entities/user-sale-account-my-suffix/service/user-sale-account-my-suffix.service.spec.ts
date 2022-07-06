import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';
import { IUserSaleAccountMySuffix, UserSaleAccountMySuffix } from '../user-sale-account-my-suffix.model';

import { UserSaleAccountMySuffixService } from './user-sale-account-my-suffix.service';

describe('UserSaleAccountMySuffix Service', () => {
  let service: UserSaleAccountMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserSaleAccountMySuffix;
  let expectedResult: IUserSaleAccountMySuffix | IUserSaleAccountMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserSaleAccountMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      balance: 0,
      lastPayment: 0,
      statut: UserSaleAccountStatut.ACTIVE,
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
          startDate: currentDate.format(DATE_TIME_FORMAT),
          endDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a UserSaleAccountMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          startDate: currentDate.format(DATE_TIME_FORMAT),
          endDate: currentDate.format(DATE_TIME_FORMAT),
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

      service.create(new UserSaleAccountMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserSaleAccountMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          balance: 1,
          lastPayment: 1,
          statut: 'BBBBBB',
          startDate: currentDate.format(DATE_TIME_FORMAT),
          endDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should partial update a UserSaleAccountMySuffix', () => {
      const patchObject = Object.assign(
        {
          startDate: currentDate.format(DATE_TIME_FORMAT),
          endDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new UserSaleAccountMySuffix()
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

    it('should return a list of UserSaleAccountMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          balance: 1,
          lastPayment: 1,
          statut: 'BBBBBB',
          startDate: currentDate.format(DATE_TIME_FORMAT),
          endDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should delete a UserSaleAccountMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserSaleAccountMySuffixToCollectionIfMissing', () => {
      it('should add a UserSaleAccountMySuffix to an empty array', () => {
        const userSaleAccount: IUserSaleAccountMySuffix = { id: 123 };
        expectedResult = service.addUserSaleAccountMySuffixToCollectionIfMissing([], userSaleAccount);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSaleAccount);
      });

      it('should not add a UserSaleAccountMySuffix to an array that contains it', () => {
        const userSaleAccount: IUserSaleAccountMySuffix = { id: 123 };
        const userSaleAccountCollection: IUserSaleAccountMySuffix[] = [
          {
            ...userSaleAccount,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserSaleAccountMySuffixToCollectionIfMissing(userSaleAccountCollection, userSaleAccount);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserSaleAccountMySuffix to an array that doesn't contain it", () => {
        const userSaleAccount: IUserSaleAccountMySuffix = { id: 123 };
        const userSaleAccountCollection: IUserSaleAccountMySuffix[] = [{ id: 456 }];
        expectedResult = service.addUserSaleAccountMySuffixToCollectionIfMissing(userSaleAccountCollection, userSaleAccount);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSaleAccount);
      });

      it('should add only unique UserSaleAccountMySuffix to an array', () => {
        const userSaleAccountArray: IUserSaleAccountMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 83765 }];
        const userSaleAccountCollection: IUserSaleAccountMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserSaleAccountMySuffixToCollectionIfMissing(userSaleAccountCollection, ...userSaleAccountArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userSaleAccount: IUserSaleAccountMySuffix = { id: 123 };
        const userSaleAccount2: IUserSaleAccountMySuffix = { id: 456 };
        expectedResult = service.addUserSaleAccountMySuffixToCollectionIfMissing([], userSaleAccount, userSaleAccount2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSaleAccount);
        expect(expectedResult).toContain(userSaleAccount2);
      });

      it('should accept null and undefined values', () => {
        const userSaleAccount: IUserSaleAccountMySuffix = { id: 123 };
        expectedResult = service.addUserSaleAccountMySuffixToCollectionIfMissing([], null, userSaleAccount, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSaleAccount);
      });

      it('should return initial array if no UserSaleAccountMySuffix is added', () => {
        const userSaleAccountCollection: IUserSaleAccountMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserSaleAccountMySuffixToCollectionIfMissing(userSaleAccountCollection, undefined, null);
        expect(expectedResult).toEqual(userSaleAccountCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
