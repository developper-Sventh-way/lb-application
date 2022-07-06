import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { ILimitConfPointMySuffix, LimitConfPointMySuffix } from '../limit-conf-point-my-suffix.model';

import { LimitConfPointMySuffixService } from './limit-conf-point-my-suffix.service';

describe('LimitConfPointMySuffix Service', () => {
  let service: LimitConfPointMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ILimitConfPointMySuffix;
  let expectedResult: ILimitConfPointMySuffix | ILimitConfPointMySuffix[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LimitConfPointMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nombreValue: 'AAAAAAA',
      limit: 0,
      limitStatut: TypeOption.MARIAGE,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LimitConfPointMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LimitConfPointMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LimitConfPointMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombreValue: 'BBBBBB',
          limit: 1,
          limitStatut: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LimitConfPointMySuffix', () => {
      const patchObject = Object.assign(
        {
          nombreValue: 'BBBBBB',
          limit: 1,
        },
        new LimitConfPointMySuffix()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LimitConfPointMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombreValue: 'BBBBBB',
          limit: 1,
          limitStatut: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LimitConfPointMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLimitConfPointMySuffixToCollectionIfMissing', () => {
      it('should add a LimitConfPointMySuffix to an empty array', () => {
        const limitConfPoint: ILimitConfPointMySuffix = { id: 123 };
        expectedResult = service.addLimitConfPointMySuffixToCollectionIfMissing([], limitConfPoint);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(limitConfPoint);
      });

      it('should not add a LimitConfPointMySuffix to an array that contains it', () => {
        const limitConfPoint: ILimitConfPointMySuffix = { id: 123 };
        const limitConfPointCollection: ILimitConfPointMySuffix[] = [
          {
            ...limitConfPoint,
          },
          { id: 456 },
        ];
        expectedResult = service.addLimitConfPointMySuffixToCollectionIfMissing(limitConfPointCollection, limitConfPoint);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LimitConfPointMySuffix to an array that doesn't contain it", () => {
        const limitConfPoint: ILimitConfPointMySuffix = { id: 123 };
        const limitConfPointCollection: ILimitConfPointMySuffix[] = [{ id: 456 }];
        expectedResult = service.addLimitConfPointMySuffixToCollectionIfMissing(limitConfPointCollection, limitConfPoint);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(limitConfPoint);
      });

      it('should add only unique LimitConfPointMySuffix to an array', () => {
        const limitConfPointArray: ILimitConfPointMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 56584 }];
        const limitConfPointCollection: ILimitConfPointMySuffix[] = [{ id: 123 }];
        expectedResult = service.addLimitConfPointMySuffixToCollectionIfMissing(limitConfPointCollection, ...limitConfPointArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const limitConfPoint: ILimitConfPointMySuffix = { id: 123 };
        const limitConfPoint2: ILimitConfPointMySuffix = { id: 456 };
        expectedResult = service.addLimitConfPointMySuffixToCollectionIfMissing([], limitConfPoint, limitConfPoint2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(limitConfPoint);
        expect(expectedResult).toContain(limitConfPoint2);
      });

      it('should accept null and undefined values', () => {
        const limitConfPoint: ILimitConfPointMySuffix = { id: 123 };
        expectedResult = service.addLimitConfPointMySuffixToCollectionIfMissing([], null, limitConfPoint, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(limitConfPoint);
      });

      it('should return initial array if no LimitConfPointMySuffix is added', () => {
        const limitConfPointCollection: ILimitConfPointMySuffix[] = [{ id: 123 }];
        expectedResult = service.addLimitConfPointMySuffixToCollectionIfMissing(limitConfPointCollection, undefined, null);
        expect(expectedResult).toEqual(limitConfPointCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
