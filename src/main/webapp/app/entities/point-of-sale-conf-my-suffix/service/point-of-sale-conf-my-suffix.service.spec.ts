import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPointOfSaleConfMySuffix, PointOfSaleConfMySuffix } from '../point-of-sale-conf-my-suffix.model';

import { PointOfSaleConfMySuffixService } from './point-of-sale-conf-my-suffix.service';

describe('PointOfSaleConfMySuffix Service', () => {
  let service: PointOfSaleConfMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IPointOfSaleConfMySuffix;
  let expectedResult: IPointOfSaleConfMySuffix | IPointOfSaleConfMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PointOfSaleConfMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      pourcentageCaissier: 0,
      pourcentageResponsable: 0,
      startTime: 'AAAAAAA',
      endTime: 'AAAAAAA',
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

    it('should create a PointOfSaleConfMySuffix', () => {
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

      service.create(new PointOfSaleConfMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PointOfSaleConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pourcentageCaissier: 1,
          pourcentageResponsable: 1,
          startTime: 'BBBBBB',
          endTime: 'BBBBBB',
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

    it('should partial update a PointOfSaleConfMySuffix', () => {
      const patchObject = Object.assign(
        {
          pourcentageCaissier: 1,
          startTime: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new PointOfSaleConfMySuffix()
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

    it('should return a list of PointOfSaleConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pourcentageCaissier: 1,
          pourcentageResponsable: 1,
          startTime: 'BBBBBB',
          endTime: 'BBBBBB',
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

    it('should delete a PointOfSaleConfMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPointOfSaleConfMySuffixToCollectionIfMissing', () => {
      it('should add a PointOfSaleConfMySuffix to an empty array', () => {
        const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 123 };
        expectedResult = service.addPointOfSaleConfMySuffixToCollectionIfMissing([], pointOfSaleConf);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfSaleConf);
      });

      it('should not add a PointOfSaleConfMySuffix to an array that contains it', () => {
        const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 123 };
        const pointOfSaleConfCollection: IPointOfSaleConfMySuffix[] = [
          {
            ...pointOfSaleConf,
          },
          { id: 456 },
        ];
        expectedResult = service.addPointOfSaleConfMySuffixToCollectionIfMissing(pointOfSaleConfCollection, pointOfSaleConf);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PointOfSaleConfMySuffix to an array that doesn't contain it", () => {
        const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 123 };
        const pointOfSaleConfCollection: IPointOfSaleConfMySuffix[] = [{ id: 456 }];
        expectedResult = service.addPointOfSaleConfMySuffixToCollectionIfMissing(pointOfSaleConfCollection, pointOfSaleConf);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfSaleConf);
      });

      it('should add only unique PointOfSaleConfMySuffix to an array', () => {
        const pointOfSaleConfArray: IPointOfSaleConfMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 9102 }];
        const pointOfSaleConfCollection: IPointOfSaleConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPointOfSaleConfMySuffixToCollectionIfMissing(pointOfSaleConfCollection, ...pointOfSaleConfArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 123 };
        const pointOfSaleConf2: IPointOfSaleConfMySuffix = { id: 456 };
        expectedResult = service.addPointOfSaleConfMySuffixToCollectionIfMissing([], pointOfSaleConf, pointOfSaleConf2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfSaleConf);
        expect(expectedResult).toContain(pointOfSaleConf2);
      });

      it('should accept null and undefined values', () => {
        const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 123 };
        expectedResult = service.addPointOfSaleConfMySuffixToCollectionIfMissing([], null, pointOfSaleConf, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfSaleConf);
      });

      it('should return initial array if no PointOfSaleConfMySuffix is added', () => {
        const pointOfSaleConfCollection: IPointOfSaleConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPointOfSaleConfMySuffixToCollectionIfMissing(pointOfSaleConfCollection, undefined, null);
        expect(expectedResult).toEqual(pointOfSaleConfCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
