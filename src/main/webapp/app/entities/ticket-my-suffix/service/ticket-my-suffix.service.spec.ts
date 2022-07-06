import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { StatutFiche } from 'app/entities/enumerations/statut-fiche.model';
import { ITicketMySuffix, TicketMySuffix } from '../ticket-my-suffix.model';

import { TicketMySuffixService } from './ticket-my-suffix.service';

describe('TicketMySuffix Service', () => {
  let service: TicketMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ITicketMySuffix;
  let expectedResult: ITicketMySuffix | ITicketMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TicketMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      ticketNo: 'AAAAAAA',
      statutFiche: StatutFiche.ACTIVE,
      closeBy: 'AAAAAAA',
      closeDate: currentDate,
      isClosed: false,
      closeById: 0,
      payBy: 'AAAAAAA',
      payDate: currentDate,
      isPay: false,
      payById: 0,
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
          closeDate: currentDate.format(DATE_TIME_FORMAT),
          payDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a TicketMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          closeDate: currentDate.format(DATE_TIME_FORMAT),
          payDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          closeDate: currentDate,
          payDate: currentDate,
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new TicketMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TicketMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ticketNo: 'BBBBBB',
          statutFiche: 'BBBBBB',
          closeBy: 'BBBBBB',
          closeDate: currentDate.format(DATE_TIME_FORMAT),
          isClosed: true,
          closeById: 1,
          payBy: 'BBBBBB',
          payDate: currentDate.format(DATE_TIME_FORMAT),
          isPay: true,
          payById: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          closeDate: currentDate,
          payDate: currentDate,
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

    it('should partial update a TicketMySuffix', () => {
      const patchObject = Object.assign(
        {
          ticketNo: 'BBBBBB',
          closeDate: currentDate.format(DATE_TIME_FORMAT),
          isClosed: true,
          closeById: 1,
          payBy: 'BBBBBB',
          isPay: true,
          createdBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new TicketMySuffix()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          closeDate: currentDate,
          payDate: currentDate,
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

    it('should return a list of TicketMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ticketNo: 'BBBBBB',
          statutFiche: 'BBBBBB',
          closeBy: 'BBBBBB',
          closeDate: currentDate.format(DATE_TIME_FORMAT),
          isClosed: true,
          closeById: 1,
          payBy: 'BBBBBB',
          payDate: currentDate.format(DATE_TIME_FORMAT),
          isPay: true,
          payById: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          closeDate: currentDate,
          payDate: currentDate,
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

    it('should delete a TicketMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTicketMySuffixToCollectionIfMissing', () => {
      it('should add a TicketMySuffix to an empty array', () => {
        const ticket: ITicketMySuffix = { id: 123 };
        expectedResult = service.addTicketMySuffixToCollectionIfMissing([], ticket);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticket);
      });

      it('should not add a TicketMySuffix to an array that contains it', () => {
        const ticket: ITicketMySuffix = { id: 123 };
        const ticketCollection: ITicketMySuffix[] = [
          {
            ...ticket,
          },
          { id: 456 },
        ];
        expectedResult = service.addTicketMySuffixToCollectionIfMissing(ticketCollection, ticket);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TicketMySuffix to an array that doesn't contain it", () => {
        const ticket: ITicketMySuffix = { id: 123 };
        const ticketCollection: ITicketMySuffix[] = [{ id: 456 }];
        expectedResult = service.addTicketMySuffixToCollectionIfMissing(ticketCollection, ticket);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticket);
      });

      it('should add only unique TicketMySuffix to an array', () => {
        const ticketArray: ITicketMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 56600 }];
        const ticketCollection: ITicketMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTicketMySuffixToCollectionIfMissing(ticketCollection, ...ticketArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ticket: ITicketMySuffix = { id: 123 };
        const ticket2: ITicketMySuffix = { id: 456 };
        expectedResult = service.addTicketMySuffixToCollectionIfMissing([], ticket, ticket2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticket);
        expect(expectedResult).toContain(ticket2);
      });

      it('should accept null and undefined values', () => {
        const ticket: ITicketMySuffix = { id: 123 };
        expectedResult = service.addTicketMySuffixToCollectionIfMissing([], null, ticket, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticket);
      });

      it('should return initial array if no TicketMySuffix is added', () => {
        const ticketCollection: ITicketMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTicketMySuffixToCollectionIfMissing(ticketCollection, undefined, null);
        expect(expectedResult).toEqual(ticketCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
