import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DeviceStatut } from 'app/entities/enumerations/device-statut.model';
import { IPOSConfigurationMySuffix, POSConfigurationMySuffix } from '../pos-configuration-my-suffix.model';

import { POSConfigurationMySuffixService } from './pos-configuration-my-suffix.service';

describe('POSConfigurationMySuffix Service', () => {
  let service: POSConfigurationMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IPOSConfigurationMySuffix;
  let expectedResult: IPOSConfigurationMySuffix | IPOSConfigurationMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(POSConfigurationMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      pOSName: 'AAAAAAA',
      iMEI: 'AAAAAAA',
      deviceStatut: DeviceStatut.ACTIVE,
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

    it('should create a POSConfigurationMySuffix', () => {
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

      service.create(new POSConfigurationMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a POSConfigurationMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pOSName: 'BBBBBB',
          iMEI: 'BBBBBB',
          deviceStatut: 'BBBBBB',
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

    it('should partial update a POSConfigurationMySuffix', () => {
      const patchObject = Object.assign(
        {
          pOSName: 'BBBBBB',
          iMEI: 'BBBBBB',
          deviceStatut: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new POSConfigurationMySuffix()
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

    it('should return a list of POSConfigurationMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pOSName: 'BBBBBB',
          iMEI: 'BBBBBB',
          deviceStatut: 'BBBBBB',
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

    it('should delete a POSConfigurationMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPOSConfigurationMySuffixToCollectionIfMissing', () => {
      it('should add a POSConfigurationMySuffix to an empty array', () => {
        const pOSConfiguration: IPOSConfigurationMySuffix = { id: 123 };
        expectedResult = service.addPOSConfigurationMySuffixToCollectionIfMissing([], pOSConfiguration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pOSConfiguration);
      });

      it('should not add a POSConfigurationMySuffix to an array that contains it', () => {
        const pOSConfiguration: IPOSConfigurationMySuffix = { id: 123 };
        const pOSConfigurationCollection: IPOSConfigurationMySuffix[] = [
          {
            ...pOSConfiguration,
          },
          { id: 456 },
        ];
        expectedResult = service.addPOSConfigurationMySuffixToCollectionIfMissing(pOSConfigurationCollection, pOSConfiguration);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a POSConfigurationMySuffix to an array that doesn't contain it", () => {
        const pOSConfiguration: IPOSConfigurationMySuffix = { id: 123 };
        const pOSConfigurationCollection: IPOSConfigurationMySuffix[] = [{ id: 456 }];
        expectedResult = service.addPOSConfigurationMySuffixToCollectionIfMissing(pOSConfigurationCollection, pOSConfiguration);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pOSConfiguration);
      });

      it('should add only unique POSConfigurationMySuffix to an array', () => {
        const pOSConfigurationArray: IPOSConfigurationMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 64702 }];
        const pOSConfigurationCollection: IPOSConfigurationMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPOSConfigurationMySuffixToCollectionIfMissing(pOSConfigurationCollection, ...pOSConfigurationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pOSConfiguration: IPOSConfigurationMySuffix = { id: 123 };
        const pOSConfiguration2: IPOSConfigurationMySuffix = { id: 456 };
        expectedResult = service.addPOSConfigurationMySuffixToCollectionIfMissing([], pOSConfiguration, pOSConfiguration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pOSConfiguration);
        expect(expectedResult).toContain(pOSConfiguration2);
      });

      it('should accept null and undefined values', () => {
        const pOSConfiguration: IPOSConfigurationMySuffix = { id: 123 };
        expectedResult = service.addPOSConfigurationMySuffixToCollectionIfMissing([], null, pOSConfiguration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pOSConfiguration);
      });

      it('should return initial array if no POSConfigurationMySuffix is added', () => {
        const pOSConfigurationCollection: IPOSConfigurationMySuffix[] = [{ id: 123 }];
        expectedResult = service.addPOSConfigurationMySuffixToCollectionIfMissing(pOSConfigurationCollection, undefined, null);
        expect(expectedResult).toEqual(pOSConfigurationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
