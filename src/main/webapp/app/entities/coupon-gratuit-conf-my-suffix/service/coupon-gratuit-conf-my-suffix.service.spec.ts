import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';
import { ICouponGratuitConfMySuffix, CouponGratuitConfMySuffix } from '../coupon-gratuit-conf-my-suffix.model';

import { CouponGratuitConfMySuffixService } from './coupon-gratuit-conf-my-suffix.service';

describe('CouponGratuitConfMySuffix Service', () => {
  let service: CouponGratuitConfMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ICouponGratuitConfMySuffix;
  let expectedResult: ICouponGratuitConfMySuffix | ICouponGratuitConfMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CouponGratuitConfMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      typeOption: TypeOption.MARIAGE,
      maximumCount: 0,
      obstinateAmount: 0,
      statut: UserStatut.ACTIVE,
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

    it('should create a CouponGratuitConfMySuffix', () => {
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

      service.create(new CouponGratuitConfMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CouponGratuitConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeOption: 'BBBBBB',
          maximumCount: 1,
          obstinateAmount: 1,
          statut: 'BBBBBB',
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

    it('should partial update a CouponGratuitConfMySuffix', () => {
      const patchObject = Object.assign(
        {
          maximumCount: 1,
          obstinateAmount: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new CouponGratuitConfMySuffix()
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

    it('should return a list of CouponGratuitConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeOption: 'BBBBBB',
          maximumCount: 1,
          obstinateAmount: 1,
          statut: 'BBBBBB',
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

    it('should delete a CouponGratuitConfMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCouponGratuitConfMySuffixToCollectionIfMissing', () => {
      it('should add a CouponGratuitConfMySuffix to an empty array', () => {
        const couponGratuitConf: ICouponGratuitConfMySuffix = { id: 123 };
        expectedResult = service.addCouponGratuitConfMySuffixToCollectionIfMissing([], couponGratuitConf);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(couponGratuitConf);
      });

      it('should not add a CouponGratuitConfMySuffix to an array that contains it', () => {
        const couponGratuitConf: ICouponGratuitConfMySuffix = { id: 123 };
        const couponGratuitConfCollection: ICouponGratuitConfMySuffix[] = [
          {
            ...couponGratuitConf,
          },
          { id: 456 },
        ];
        expectedResult = service.addCouponGratuitConfMySuffixToCollectionIfMissing(couponGratuitConfCollection, couponGratuitConf);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CouponGratuitConfMySuffix to an array that doesn't contain it", () => {
        const couponGratuitConf: ICouponGratuitConfMySuffix = { id: 123 };
        const couponGratuitConfCollection: ICouponGratuitConfMySuffix[] = [{ id: 456 }];
        expectedResult = service.addCouponGratuitConfMySuffixToCollectionIfMissing(couponGratuitConfCollection, couponGratuitConf);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(couponGratuitConf);
      });

      it('should add only unique CouponGratuitConfMySuffix to an array', () => {
        const couponGratuitConfArray: ICouponGratuitConfMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 80946 }];
        const couponGratuitConfCollection: ICouponGratuitConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addCouponGratuitConfMySuffixToCollectionIfMissing(couponGratuitConfCollection, ...couponGratuitConfArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const couponGratuitConf: ICouponGratuitConfMySuffix = { id: 123 };
        const couponGratuitConf2: ICouponGratuitConfMySuffix = { id: 456 };
        expectedResult = service.addCouponGratuitConfMySuffixToCollectionIfMissing([], couponGratuitConf, couponGratuitConf2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(couponGratuitConf);
        expect(expectedResult).toContain(couponGratuitConf2);
      });

      it('should accept null and undefined values', () => {
        const couponGratuitConf: ICouponGratuitConfMySuffix = { id: 123 };
        expectedResult = service.addCouponGratuitConfMySuffixToCollectionIfMissing([], null, couponGratuitConf, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(couponGratuitConf);
      });

      it('should return initial array if no CouponGratuitConfMySuffix is added', () => {
        const couponGratuitConfCollection: ICouponGratuitConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addCouponGratuitConfMySuffixToCollectionIfMissing(couponGratuitConfCollection, undefined, null);
        expect(expectedResult).toEqual(couponGratuitConfCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
