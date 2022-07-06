import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TirageName } from 'app/entities/enumerations/tirage-name.model';
import { IBorletteConfMySuffix, BorletteConfMySuffix } from '../borlette-conf-my-suffix.model';

import { BorletteConfMySuffixService } from './borlette-conf-my-suffix.service';

describe('BorletteConfMySuffix Service', () => {
  let service: BorletteConfMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IBorletteConfMySuffix;
  let expectedResult: IBorletteConfMySuffix | IBorletteConfMySuffix[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BorletteConfMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: TirageName.NEWYORK,
      premierLot: 0,
      deuxiemeLot: 0,
      troisiemeLot: 0,
      mariageGratisPrix: 0,
      montantMinimum: 0,
      montantMaximum: 0,
      closeTimeMidi: 'AAAAAAA',
      closeTimeSoir: 'AAAAAAA',
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

    it('should create a BorletteConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BorletteConfMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BorletteConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          premierLot: 1,
          deuxiemeLot: 1,
          troisiemeLot: 1,
          mariageGratisPrix: 1,
          montantMinimum: 1,
          montantMaximum: 1,
          closeTimeMidi: 'BBBBBB',
          closeTimeSoir: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BorletteConfMySuffix', () => {
      const patchObject = Object.assign(
        {
          mariageGratisPrix: 1,
          montantMaximum: 1,
        },
        new BorletteConfMySuffix()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BorletteConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          premierLot: 1,
          deuxiemeLot: 1,
          troisiemeLot: 1,
          mariageGratisPrix: 1,
          montantMinimum: 1,
          montantMaximum: 1,
          closeTimeMidi: 'BBBBBB',
          closeTimeSoir: 'BBBBBB',
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

    it('should delete a BorletteConfMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBorletteConfMySuffixToCollectionIfMissing', () => {
      it('should add a BorletteConfMySuffix to an empty array', () => {
        const borletteConf: IBorletteConfMySuffix = { id: 123 };
        expectedResult = service.addBorletteConfMySuffixToCollectionIfMissing([], borletteConf);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(borletteConf);
      });

      it('should not add a BorletteConfMySuffix to an array that contains it', () => {
        const borletteConf: IBorletteConfMySuffix = { id: 123 };
        const borletteConfCollection: IBorletteConfMySuffix[] = [
          {
            ...borletteConf,
          },
          { id: 456 },
        ];
        expectedResult = service.addBorletteConfMySuffixToCollectionIfMissing(borletteConfCollection, borletteConf);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BorletteConfMySuffix to an array that doesn't contain it", () => {
        const borletteConf: IBorletteConfMySuffix = { id: 123 };
        const borletteConfCollection: IBorletteConfMySuffix[] = [{ id: 456 }];
        expectedResult = service.addBorletteConfMySuffixToCollectionIfMissing(borletteConfCollection, borletteConf);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(borletteConf);
      });

      it('should add only unique BorletteConfMySuffix to an array', () => {
        const borletteConfArray: IBorletteConfMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 40018 }];
        const borletteConfCollection: IBorletteConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addBorletteConfMySuffixToCollectionIfMissing(borletteConfCollection, ...borletteConfArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const borletteConf: IBorletteConfMySuffix = { id: 123 };
        const borletteConf2: IBorletteConfMySuffix = { id: 456 };
        expectedResult = service.addBorletteConfMySuffixToCollectionIfMissing([], borletteConf, borletteConf2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(borletteConf);
        expect(expectedResult).toContain(borletteConf2);
      });

      it('should accept null and undefined values', () => {
        const borletteConf: IBorletteConfMySuffix = { id: 123 };
        expectedResult = service.addBorletteConfMySuffixToCollectionIfMissing([], null, borletteConf, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(borletteConf);
      });

      it('should return initial array if no BorletteConfMySuffix is added', () => {
        const borletteConfCollection: IBorletteConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addBorletteConfMySuffixToCollectionIfMissing(borletteConfCollection, undefined, null);
        expect(expectedResult).toEqual(borletteConfCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
