import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { StatutBanque } from 'app/entities/enumerations/statut-banque.model';
import { IPointOfSaleMySuffix, PointOfSaleMySuffix } from '../point-of-sale-my-suffix.model';

import { PointOfSaleMySuffixService } from './point-of-sale-my-suffix.service';

describe('PointOfSaleMySuffix Service', () => {
  let service: PointOfSaleMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IPointOfSaleMySuffix;
  let expectedResult: IPointOfSaleMySuffix | IPointOfSaleMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PointOfSaleMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      adressePoint: 'AAAAAAA',
      statut: StatutBanque.OPEN,
      phone1: 'AAAAAAA',
      phone2: 'AAAAAAA',
      pourcentagePoint: 0,
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

    it('should create a PointOfSaleMySuffix', () => {
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

      service.create(new PointOfSaleMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PointOfSaleMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          adressePoint: 'BBBBBB',
          statut: 'BBBBBB',
          phone1: 'BBBBBB',
          phone2: 'BBBBBB',
          pourcentagePoint: 1,
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

    it('should partial update a PointOfSaleMySuffix', () => {
      const patchObject = Object.assign(
        {
          phone2: 'BBBBBB',
          pourcentagePoint: 1,
          createdBy: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new PointOfSaleMySuffix()
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

    it('should return a list of PointOfSaleMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          adressePoint: 'BBBBBB',
          statut: 'BBBBBB',
          phone1: 'BBBBBB',
          phone2: 'BBBBBB',
          pourcentagePoint: 1,
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

    it('should delete a PointOfSaleMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPointOfSaleMySuffixToCollectionIfMissing', () => {
      it('should add a PointOfSaleMySuffix to an empty array', () => {
        const pointOfSale: IPointOfSaleMySuffix = { id: 123 };
        expectedResult = service.addPointOfSaleMySuffixToCollectionIfMissing([], pointOfSale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfSale);
      });

      it('should not add a PointOfSaleMySuffix to an array that contains it', () => {
        const pointOfSale: IPointOfSaleMySuffix = { id: 123 };
        const pointOfSaleCollection: IPointOfSaleMySuffix[] = [
          {
            ...pointOfSale,
          },
          { id: 456 },
        ];
        expectedResult = service.addPointOfSaleMySuffixToCollectionIfMissing(pointOfSaleCollection, pointOfSale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PointOfSaleMySuffix to an array that doesn't contain it", () => {
        const pointOfSale: IPointOfSaleMySuffix = { id: 123 };
        const pointOfSaleCollection: IPointOfSaleMySuffix[] = [{ id: 456 }];
        expectedResult = service.addPointOfSaleMySuffixToCollectionIfMissing(pointOfSaleCollection, pointOfSale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfSale);
      });

      it('should add only unique PointOfSaleMySuffix to an array', () => {
        const pointOfSaleArray: IPointOfSaleMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 19421 }];
        const pointOfSaleCollection: IPointOfSaleMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPointOfSaleMySuffixToCollectionIfMissing(pointOfSaleCollection, ...pointOfSaleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointOfSale: IPointOfSaleMySuffix = { id: 123 };
        const pointOfSale2: IPointOfSaleMySuffix = { id: 456 };
        expectedResult = service.addPointOfSaleMySuffixToCollectionIfMissing([], pointOfSale, pointOfSale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfSale);
        expect(expectedResult).toContain(pointOfSale2);
      });

      it('should accept null and undefined values', () => {
        const pointOfSale: IPointOfSaleMySuffix = { id: 123 };
        expectedResult = service.addPointOfSaleMySuffixToCollectionIfMissing([], null, pointOfSale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfSale);
      });

      it('should return initial array if no PointOfSaleMySuffix is added', () => {
        const pointOfSaleCollection: IPointOfSaleMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPointOfSaleMySuffixToCollectionIfMissing(pointOfSaleCollection, undefined, null);
        expect(expectedResult).toEqual(pointOfSaleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
