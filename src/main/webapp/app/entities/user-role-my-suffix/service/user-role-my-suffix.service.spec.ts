import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { UserRoleName } from 'app/entities/enumerations/user-role-name.model';
import { IUserRoleMySuffix, UserRoleMySuffix } from '../user-role-my-suffix.model';

import { UserRoleMySuffixService } from './user-role-my-suffix.service';

describe('UserRoleMySuffix Service', () => {
  let service: UserRoleMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserRoleMySuffix;
  let expectedResult: IUserRoleMySuffix | IUserRoleMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserRoleMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      role: UserRoleName.CASHIER,
      description: 'AAAAAAA',
      exceptions: 'AAAAAAA',
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

    it('should create a UserRoleMySuffix', () => {
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

      service.create(new UserRoleMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserRoleMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          role: 'BBBBBB',
          description: 'BBBBBB',
          exceptions: 'BBBBBB',
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

    it('should partial update a UserRoleMySuffix', () => {
      const patchObject = Object.assign(
        {
          role: 'BBBBBB',
          description: 'BBBBBB',
          exceptions: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new UserRoleMySuffix()
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

    it('should return a list of UserRoleMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          role: 'BBBBBB',
          description: 'BBBBBB',
          exceptions: 'BBBBBB',
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

    it('should delete a UserRoleMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserRoleMySuffixToCollectionIfMissing', () => {
      it('should add a UserRoleMySuffix to an empty array', () => {
        const userRole: IUserRoleMySuffix = { id: 123 };
        expectedResult = service.addUserRoleMySuffixToCollectionIfMissing([], userRole);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userRole);
      });

      it('should not add a UserRoleMySuffix to an array that contains it', () => {
        const userRole: IUserRoleMySuffix = { id: 123 };
        const userRoleCollection: IUserRoleMySuffix[] = [
          {
            ...userRole,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserRoleMySuffixToCollectionIfMissing(userRoleCollection, userRole);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserRoleMySuffix to an array that doesn't contain it", () => {
        const userRole: IUserRoleMySuffix = { id: 123 };
        const userRoleCollection: IUserRoleMySuffix[] = [{ id: 456 }];
        expectedResult = service.addUserRoleMySuffixToCollectionIfMissing(userRoleCollection, userRole);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userRole);
      });

      it('should add only unique UserRoleMySuffix to an array', () => {
        const userRoleArray: IUserRoleMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 81179 }];
        const userRoleCollection: IUserRoleMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserRoleMySuffixToCollectionIfMissing(userRoleCollection, ...userRoleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userRole: IUserRoleMySuffix = { id: 123 };
        const userRole2: IUserRoleMySuffix = { id: 456 };
        expectedResult = service.addUserRoleMySuffixToCollectionIfMissing([], userRole, userRole2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userRole);
        expect(expectedResult).toContain(userRole2);
      });

      it('should accept null and undefined values', () => {
        const userRole: IUserRoleMySuffix = { id: 123 };
        expectedResult = service.addUserRoleMySuffixToCollectionIfMissing([], null, userRole, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userRole);
      });

      it('should return initial array if no UserRoleMySuffix is added', () => {
        const userRoleCollection: IUserRoleMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUserRoleMySuffixToCollectionIfMissing(userRoleCollection, undefined, null);
        expect(expectedResult).toEqual(userRoleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
