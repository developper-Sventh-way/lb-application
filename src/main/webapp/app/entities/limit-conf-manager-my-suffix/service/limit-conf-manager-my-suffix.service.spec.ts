import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { ILimitConfManagerMySuffix, LimitConfManagerMySuffix } from '../limit-conf-manager-my-suffix.model';

import { LimitConfManagerMySuffixService } from './limit-conf-manager-my-suffix.service';

describe('LimitConfManagerMySuffix Service', () => {
  let service: LimitConfManagerMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ILimitConfManagerMySuffix;
  let expectedResult: ILimitConfManagerMySuffix | ILimitConfManagerMySuffix[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LimitConfManagerMySuffixService);
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

    it('should create a LimitConfManagerMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LimitConfManagerMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LimitConfManagerMySuffix', () => {
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

    it('should partial update a LimitConfManagerMySuffix', () => {
      const patchObject = Object.assign(
        {
          limitStatut: 'BBBBBB',
        },
        new LimitConfManagerMySuffix()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LimitConfManagerMySuffix', () => {
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

    it('should delete a LimitConfManagerMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLimitConfManagerMySuffixToCollectionIfMissing', () => {
      it('should add a LimitConfManagerMySuffix to an empty array', () => {
        const limitConfManager: ILimitConfManagerMySuffix = { id: 123 };
        expectedResult = service.addLimitConfManagerMySuffixToCollectionIfMissing([], limitConfManager);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(limitConfManager);
      });

      it('should not add a LimitConfManagerMySuffix to an array that contains it', () => {
        const limitConfManager: ILimitConfManagerMySuffix = { id: 123 };
        const limitConfManagerCollection: ILimitConfManagerMySuffix[] = [
          {
            ...limitConfManager,
          },
          { id: 456 },
        ];
        expectedResult = service.addLimitConfManagerMySuffixToCollectionIfMissing(limitConfManagerCollection, limitConfManager);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LimitConfManagerMySuffix to an array that doesn't contain it", () => {
        const limitConfManager: ILimitConfManagerMySuffix = { id: 123 };
        const limitConfManagerCollection: ILimitConfManagerMySuffix[] = [{ id: 456 }];
        expectedResult = service.addLimitConfManagerMySuffixToCollectionIfMissing(limitConfManagerCollection, limitConfManager);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(limitConfManager);
      });

      it('should add only unique LimitConfManagerMySuffix to an array', () => {
        const limitConfManagerArray: ILimitConfManagerMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 57206 }];
        const limitConfManagerCollection: ILimitConfManagerMySuffix[] = [{ id: 123 }];
        expectedResult = service.addLimitConfManagerMySuffixToCollectionIfMissing(limitConfManagerCollection, ...limitConfManagerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const limitConfManager: ILimitConfManagerMySuffix = { id: 123 };
        const limitConfManager2: ILimitConfManagerMySuffix = { id: 456 };
        expectedResult = service.addLimitConfManagerMySuffixToCollectionIfMissing([], limitConfManager, limitConfManager2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(limitConfManager);
        expect(expectedResult).toContain(limitConfManager2);
      });

      it('should accept null and undefined values', () => {
        const limitConfManager: ILimitConfManagerMySuffix = { id: 123 };
        expectedResult = service.addLimitConfManagerMySuffixToCollectionIfMissing([], null, limitConfManager, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(limitConfManager);
      });

      it('should return initial array if no LimitConfManagerMySuffix is added', () => {
        const limitConfManagerCollection: ILimitConfManagerMySuffix[] = [{ id: 123 }];
        expectedResult = service.addLimitConfManagerMySuffixToCollectionIfMissing(limitConfManagerCollection, undefined, null);
        expect(expectedResult).toEqual(limitConfManagerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
