import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { ILimitConfBorletteMySuffix, LimitConfBorletteMySuffix } from '../limit-conf-borlette-my-suffix.model';

import { LimitConfBorletteMySuffixService } from './limit-conf-borlette-my-suffix.service';

describe('LimitConfBorletteMySuffix Service', () => {
  let service: LimitConfBorletteMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ILimitConfBorletteMySuffix;
  let expectedResult: ILimitConfBorletteMySuffix | ILimitConfBorletteMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LimitConfBorletteMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nombreValue: 'AAAAAAA',
      limit: 0,
      limitStatut: TypeOption.MARIAGE,
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

    it('should create a LimitConfBorletteMySuffix', () => {
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

      service.create(new LimitConfBorletteMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LimitConfBorletteMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombreValue: 'BBBBBB',
          limit: 1,
          limitStatut: 'BBBBBB',
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

    it('should partial update a LimitConfBorletteMySuffix', () => {
      const patchObject = Object.assign(
        {
          nombreValue: 'BBBBBB',
          limit: 1,
          limitStatut: 'BBBBBB',
          createdBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new LimitConfBorletteMySuffix()
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

    it('should return a list of LimitConfBorletteMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombreValue: 'BBBBBB',
          limit: 1,
          limitStatut: 'BBBBBB',
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

    it('should delete a LimitConfBorletteMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLimitConfBorletteMySuffixToCollectionIfMissing', () => {
      it('should add a LimitConfBorletteMySuffix to an empty array', () => {
        const limitConfBorlette: ILimitConfBorletteMySuffix = { id: 123 };
        expectedResult = service.addLimitConfBorletteMySuffixToCollectionIfMissing([], limitConfBorlette);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(limitConfBorlette);
      });

      it('should not add a LimitConfBorletteMySuffix to an array that contains it', () => {
        const limitConfBorlette: ILimitConfBorletteMySuffix = { id: 123 };
        const limitConfBorletteCollection: ILimitConfBorletteMySuffix[] = [
          {
            ...limitConfBorlette,
          },
          { id: 456 },
        ];
        expectedResult = service.addLimitConfBorletteMySuffixToCollectionIfMissing(limitConfBorletteCollection, limitConfBorlette);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LimitConfBorletteMySuffix to an array that doesn't contain it", () => {
        const limitConfBorlette: ILimitConfBorletteMySuffix = { id: 123 };
        const limitConfBorletteCollection: ILimitConfBorletteMySuffix[] = [{ id: 456 }];
        expectedResult = service.addLimitConfBorletteMySuffixToCollectionIfMissing(limitConfBorletteCollection, limitConfBorlette);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(limitConfBorlette);
      });

      it('should add only unique LimitConfBorletteMySuffix to an array', () => {
        const limitConfBorletteArray: ILimitConfBorletteMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 35408 }];
        const limitConfBorletteCollection: ILimitConfBorletteMySuffix[] = [{ id: 123 }];
        expectedResult = service.addLimitConfBorletteMySuffixToCollectionIfMissing(limitConfBorletteCollection, ...limitConfBorletteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const limitConfBorlette: ILimitConfBorletteMySuffix = { id: 123 };
        const limitConfBorlette2: ILimitConfBorletteMySuffix = { id: 456 };
        expectedResult = service.addLimitConfBorletteMySuffixToCollectionIfMissing([], limitConfBorlette, limitConfBorlette2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(limitConfBorlette);
        expect(expectedResult).toContain(limitConfBorlette2);
      });

      it('should accept null and undefined values', () => {
        const limitConfBorlette: ILimitConfBorletteMySuffix = { id: 123 };
        expectedResult = service.addLimitConfBorletteMySuffixToCollectionIfMissing([], null, limitConfBorlette, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(limitConfBorlette);
      });

      it('should return initial array if no LimitConfBorletteMySuffix is added', () => {
        const limitConfBorletteCollection: ILimitConfBorletteMySuffix[] = [{ id: 123 }];
        expectedResult = service.addLimitConfBorletteMySuffixToCollectionIfMissing(limitConfBorletteCollection, undefined, null);
        expect(expectedResult).toEqual(limitConfBorletteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
