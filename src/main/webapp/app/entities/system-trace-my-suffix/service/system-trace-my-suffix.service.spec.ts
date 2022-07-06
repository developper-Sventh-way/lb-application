import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISystemTraceMySuffix, SystemTraceMySuffix } from '../system-trace-my-suffix.model';

import { SystemTraceMySuffixService } from './system-trace-my-suffix.service';

describe('SystemTraceMySuffix Service', () => {
  let service: SystemTraceMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ISystemTraceMySuffix;
  let expectedResult: ISystemTraceMySuffix | ISystemTraceMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SystemTraceMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      traceContenu: 'AAAAAAA',
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

    it('should create a SystemTraceMySuffix', () => {
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

      service.create(new SystemTraceMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SystemTraceMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          traceContenu: 'BBBBBB',
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

    it('should partial update a SystemTraceMySuffix', () => {
      const patchObject = Object.assign(
        {
          traceContenu: 'BBBBBB',
          createdBy: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new SystemTraceMySuffix()
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

    it('should return a list of SystemTraceMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          traceContenu: 'BBBBBB',
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

    it('should delete a SystemTraceMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSystemTraceMySuffixToCollectionIfMissing', () => {
      it('should add a SystemTraceMySuffix to an empty array', () => {
        const systemTrace: ISystemTraceMySuffix = { id: 123 };
        expectedResult = service.addSystemTraceMySuffixToCollectionIfMissing([], systemTrace);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(systemTrace);
      });

      it('should not add a SystemTraceMySuffix to an array that contains it', () => {
        const systemTrace: ISystemTraceMySuffix = { id: 123 };
        const systemTraceCollection: ISystemTraceMySuffix[] = [
          {
            ...systemTrace,
          },
          { id: 456 },
        ];
        expectedResult = service.addSystemTraceMySuffixToCollectionIfMissing(systemTraceCollection, systemTrace);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SystemTraceMySuffix to an array that doesn't contain it", () => {
        const systemTrace: ISystemTraceMySuffix = { id: 123 };
        const systemTraceCollection: ISystemTraceMySuffix[] = [{ id: 456 }];
        expectedResult = service.addSystemTraceMySuffixToCollectionIfMissing(systemTraceCollection, systemTrace);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(systemTrace);
      });

      it('should add only unique SystemTraceMySuffix to an array', () => {
        const systemTraceArray: ISystemTraceMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 8098 }];
        const systemTraceCollection: ISystemTraceMySuffix[] = [{ id: 123 }];
        expectedResult = service.addSystemTraceMySuffixToCollectionIfMissing(systemTraceCollection, ...systemTraceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const systemTrace: ISystemTraceMySuffix = { id: 123 };
        const systemTrace2: ISystemTraceMySuffix = { id: 456 };
        expectedResult = service.addSystemTraceMySuffixToCollectionIfMissing([], systemTrace, systemTrace2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(systemTrace);
        expect(expectedResult).toContain(systemTrace2);
      });

      it('should accept null and undefined values', () => {
        const systemTrace: ISystemTraceMySuffix = { id: 123 };
        expectedResult = service.addSystemTraceMySuffixToCollectionIfMissing([], null, systemTrace, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(systemTrace);
      });

      it('should return initial array if no SystemTraceMySuffix is added', () => {
        const systemTraceCollection: ISystemTraceMySuffix[] = [{ id: 123 }];
        expectedResult = service.addSystemTraceMySuffixToCollectionIfMissing(systemTraceCollection, undefined, null);
        expect(expectedResult).toEqual(systemTraceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
