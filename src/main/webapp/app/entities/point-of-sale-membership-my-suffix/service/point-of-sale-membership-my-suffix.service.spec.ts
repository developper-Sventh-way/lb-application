import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TypeBanque } from 'app/entities/enumerations/type-banque.model';
import { IPointOfSaleMembershipMySuffix, PointOfSaleMembershipMySuffix } from '../point-of-sale-membership-my-suffix.model';

import { PointOfSaleMembershipMySuffixService } from './point-of-sale-membership-my-suffix.service';

describe('PointOfSaleMembershipMySuffix Service', () => {
  let service: PointOfSaleMembershipMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IPointOfSaleMembershipMySuffix;
  let expectedResult: IPointOfSaleMembershipMySuffix | IPointOfSaleMembershipMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PointOfSaleMembershipMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      typePoint: TypeBanque.INTERNE,
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

    it('should create a PointOfSaleMembershipMySuffix', () => {
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

      service.create(new PointOfSaleMembershipMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PointOfSaleMembershipMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typePoint: 'BBBBBB',
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

    it('should partial update a PointOfSaleMembershipMySuffix', () => {
      const patchObject = Object.assign(
        {
          createdBy: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new PointOfSaleMembershipMySuffix()
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

    it('should return a list of PointOfSaleMembershipMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typePoint: 'BBBBBB',
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

    it('should delete a PointOfSaleMembershipMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPointOfSaleMembershipMySuffixToCollectionIfMissing', () => {
      it('should add a PointOfSaleMembershipMySuffix to an empty array', () => {
        const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 123 };
        expectedResult = service.addPointOfSaleMembershipMySuffixToCollectionIfMissing([], pointOfSaleMembership);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfSaleMembership);
      });

      it('should not add a PointOfSaleMembershipMySuffix to an array that contains it', () => {
        const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 123 };
        const pointOfSaleMembershipCollection: IPointOfSaleMembershipMySuffix[] = [
          {
            ...pointOfSaleMembership,
          },
          { id: 456 },
        ];
        expectedResult = service.addPointOfSaleMembershipMySuffixToCollectionIfMissing(
          pointOfSaleMembershipCollection,
          pointOfSaleMembership
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PointOfSaleMembershipMySuffix to an array that doesn't contain it", () => {
        const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 123 };
        const pointOfSaleMembershipCollection: IPointOfSaleMembershipMySuffix[] = [{ id: 456 }];
        expectedResult = service.addPointOfSaleMembershipMySuffixToCollectionIfMissing(
          pointOfSaleMembershipCollection,
          pointOfSaleMembership
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfSaleMembership);
      });

      it('should add only unique PointOfSaleMembershipMySuffix to an array', () => {
        const pointOfSaleMembershipArray: IPointOfSaleMembershipMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 79138 }];
        const pointOfSaleMembershipCollection: IPointOfSaleMembershipMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPointOfSaleMembershipMySuffixToCollectionIfMissing(
          pointOfSaleMembershipCollection,
          ...pointOfSaleMembershipArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 123 };
        const pointOfSaleMembership2: IPointOfSaleMembershipMySuffix = { id: 456 };
        expectedResult = service.addPointOfSaleMembershipMySuffixToCollectionIfMissing([], pointOfSaleMembership, pointOfSaleMembership2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointOfSaleMembership);
        expect(expectedResult).toContain(pointOfSaleMembership2);
      });

      it('should accept null and undefined values', () => {
        const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 123 };
        expectedResult = service.addPointOfSaleMembershipMySuffixToCollectionIfMissing([], null, pointOfSaleMembership, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointOfSaleMembership);
      });

      it('should return initial array if no PointOfSaleMembershipMySuffix is added', () => {
        const pointOfSaleMembershipCollection: IPointOfSaleMembershipMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPointOfSaleMembershipMySuffixToCollectionIfMissing(pointOfSaleMembershipCollection, undefined, null);
        expect(expectedResult).toEqual(pointOfSaleMembershipCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
