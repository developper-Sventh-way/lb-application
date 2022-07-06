import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { StatutFiche } from 'app/entities/enumerations/statut-fiche.model';
import { ITicketOptionMySuffix, TicketOptionMySuffix } from '../ticket-option-my-suffix.model';

import { TicketOptionMySuffixService } from './ticket-option-my-suffix.service';

describe('TicketOptionMySuffix Service', () => {
  let service: TicketOptionMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: ITicketOptionMySuffix;
  let expectedResult: ITicketOptionMySuffix | ITicketOptionMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TicketOptionMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      contenu: 'AAAAAAA',
      playAmount: 0,
      typeOption: TypeOption.MARIAGE,
      statutOption: StatutFiche.ACTIVE,
      multiplicateur: 0,
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

    it('should create a TicketOptionMySuffix', () => {
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

      service.create(new TicketOptionMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TicketOptionMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contenu: 'BBBBBB',
          playAmount: 1,
          typeOption: 'BBBBBB',
          statutOption: 'BBBBBB',
          multiplicateur: 1,
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

    it('should partial update a TicketOptionMySuffix', () => {
      const patchObject = Object.assign(
        {
          playAmount: 1,
          statutOption: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new TicketOptionMySuffix()
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

    it('should return a list of TicketOptionMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contenu: 'BBBBBB',
          playAmount: 1,
          typeOption: 'BBBBBB',
          statutOption: 'BBBBBB',
          multiplicateur: 1,
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

    it('should delete a TicketOptionMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTicketOptionMySuffixToCollectionIfMissing', () => {
      it('should add a TicketOptionMySuffix to an empty array', () => {
        const ticketOption: ITicketOptionMySuffix = { id: 123 };
        expectedResult = service.addTicketOptionMySuffixToCollectionIfMissing([], ticketOption);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticketOption);
      });

      it('should not add a TicketOptionMySuffix to an array that contains it', () => {
        const ticketOption: ITicketOptionMySuffix = { id: 123 };
        const ticketOptionCollection: ITicketOptionMySuffix[] = [
          {
            ...ticketOption,
          },
          { id: 456 },
        ];
        expectedResult = service.addTicketOptionMySuffixToCollectionIfMissing(ticketOptionCollection, ticketOption);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TicketOptionMySuffix to an array that doesn't contain it", () => {
        const ticketOption: ITicketOptionMySuffix = { id: 123 };
        const ticketOptionCollection: ITicketOptionMySuffix[] = [{ id: 456 }];
        expectedResult = service.addTicketOptionMySuffixToCollectionIfMissing(ticketOptionCollection, ticketOption);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticketOption);
      });

      it('should add only unique TicketOptionMySuffix to an array', () => {
        const ticketOptionArray: ITicketOptionMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 18888 }];
        const ticketOptionCollection: ITicketOptionMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTicketOptionMySuffixToCollectionIfMissing(ticketOptionCollection, ...ticketOptionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ticketOption: ITicketOptionMySuffix = { id: 123 };
        const ticketOption2: ITicketOptionMySuffix = { id: 456 };
        expectedResult = service.addTicketOptionMySuffixToCollectionIfMissing([], ticketOption, ticketOption2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticketOption);
        expect(expectedResult).toContain(ticketOption2);
      });

      it('should accept null and undefined values', () => {
        const ticketOption: ITicketOptionMySuffix = { id: 123 };
        expectedResult = service.addTicketOptionMySuffixToCollectionIfMissing([], null, ticketOption, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticketOption);
      });

      it('should return initial array if no TicketOptionMySuffix is added', () => {
        const ticketOptionCollection: ITicketOptionMySuffix[] = [{ id: 123 }];
        expectedResult = service.addTicketOptionMySuffixToCollectionIfMissing(ticketOptionCollection, undefined, null);
        expect(expectedResult).toEqual(ticketOptionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
