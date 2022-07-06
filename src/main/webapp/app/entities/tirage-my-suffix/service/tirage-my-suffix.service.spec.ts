import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TirageName } from 'app/entities/enumerations/tirage-name.model';
import { TirageType } from 'app/entities/enumerations/tirage-type.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';
import { ITirageMySuffix, TirageMySuffix } from '../tirage-my-suffix.model';

import { TirageMySuffixService } from './tirage-my-suffix.service';

describe('TirageMySuffix Service', () => {
  let service: TirageMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ITirageMySuffix;
  let expectedResult: ITirageMySuffix | ITirageMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TirageMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      tirageName: TirageName.NEWYORK,
      type: TirageType.MIDI,
      premierLot: 'AAAAAAA',
      deuxiemeLot: 'AAAAAAA',
      troisiemeLot: 'AAAAAAA',
      loto3Chif: 'AAAAAAA',
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

    it('should create a TirageMySuffix', () => {
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

      service.create(new TirageMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TirageMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tirageName: 'BBBBBB',
          type: 'BBBBBB',
          premierLot: 'BBBBBB',
          deuxiemeLot: 'BBBBBB',
          troisiemeLot: 'BBBBBB',
          loto3Chif: 'BBBBBB',
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

    it('should partial update a TirageMySuffix', () => {
      const patchObject = Object.assign(
        {
          tirageName: 'BBBBBB',
          type: 'BBBBBB',
          deuxiemeLot: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new TirageMySuffix()
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

    it('should return a list of TirageMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tirageName: 'BBBBBB',
          type: 'BBBBBB',
          premierLot: 'BBBBBB',
          deuxiemeLot: 'BBBBBB',
          troisiemeLot: 'BBBBBB',
          loto3Chif: 'BBBBBB',
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

    it('should delete a TirageMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTirageMySuffixToCollectionIfMissing', () => {
      it('should add a TirageMySuffix to an empty array', () => {
        const tirage: ITirageMySuffix = { id: 123 };
        expectedResult = service.addTirageMySuffixToCollectionIfMissing([], tirage);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tirage);
      });

      it('should not add a TirageMySuffix to an array that contains it', () => {
        const tirage: ITirageMySuffix = { id: 123 };
        const tirageCollection: ITirageMySuffix[] = [
          {
            ...tirage,
          },
          { id: 456 },
        ];
        expectedResult = service.addTirageMySuffixToCollectionIfMissing(tirageCollection, tirage);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TirageMySuffix to an array that doesn't contain it", () => {
        const tirage: ITirageMySuffix = { id: 123 };
        const tirageCollection: ITirageMySuffix[] = [{ id: 456 }];
        expectedResult = service.addTirageMySuffixToCollectionIfMissing(tirageCollection, tirage);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tirage);
      });

      it('should add only unique TirageMySuffix to an array', () => {
        const tirageArray: ITirageMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 54464 }];
        const tirageCollection: ITirageMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTirageMySuffixToCollectionIfMissing(tirageCollection, ...tirageArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tirage: ITirageMySuffix = { id: 123 };
        const tirage2: ITirageMySuffix = { id: 456 };
        expectedResult = service.addTirageMySuffixToCollectionIfMissing([], tirage, tirage2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tirage);
        expect(expectedResult).toContain(tirage2);
      });

      it('should accept null and undefined values', () => {
        const tirage: ITirageMySuffix = { id: 123 };
        expectedResult = service.addTirageMySuffixToCollectionIfMissing([], null, tirage, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tirage);
      });

      it('should return initial array if no TirageMySuffix is added', () => {
        const tirageCollection: ITirageMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTirageMySuffixToCollectionIfMissing(tirageCollection, undefined, null);
        expect(expectedResult).toEqual(tirageCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
