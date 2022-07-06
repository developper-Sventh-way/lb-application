import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMembershipConfMySuffix, MembershipConfMySuffix } from '../membership-conf-my-suffix.model';

import { MembershipConfMySuffixService } from './membership-conf-my-suffix.service';

describe('MembershipConfMySuffix Service', () => {
  let service: MembershipConfMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IMembershipConfMySuffix;
  let expectedResult: IMembershipConfMySuffix | IMembershipConfMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MembershipConfMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nomClient: 'AAAAAAA',
      slogan: 'AAAAAAA',
      telephones: 'AAAAAAA',
      adresse: 'AAAAAAA',
      infos: 'AAAAAAA',
      logoLink: 'AAAAAAA',
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

    it('should create a MembershipConfMySuffix', () => {
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

      service.create(new MembershipConfMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MembershipConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomClient: 'BBBBBB',
          slogan: 'BBBBBB',
          telephones: 'BBBBBB',
          adresse: 'BBBBBB',
          infos: 'BBBBBB',
          logoLink: 'BBBBBB',
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

    it('should partial update a MembershipConfMySuffix', () => {
      const patchObject = Object.assign(
        {
          nomClient: 'BBBBBB',
          slogan: 'BBBBBB',
          adresse: 'BBBBBB',
          logoLink: 'BBBBBB',
          createdBy: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new MembershipConfMySuffix()
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

    it('should return a list of MembershipConfMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomClient: 'BBBBBB',
          slogan: 'BBBBBB',
          telephones: 'BBBBBB',
          adresse: 'BBBBBB',
          infos: 'BBBBBB',
          logoLink: 'BBBBBB',
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

    it('should delete a MembershipConfMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMembershipConfMySuffixToCollectionIfMissing', () => {
      it('should add a MembershipConfMySuffix to an empty array', () => {
        const membershipConf: IMembershipConfMySuffix = { id: 123 };
        expectedResult = service.addMembershipConfMySuffixToCollectionIfMissing([], membershipConf);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(membershipConf);
      });

      it('should not add a MembershipConfMySuffix to an array that contains it', () => {
        const membershipConf: IMembershipConfMySuffix = { id: 123 };
        const membershipConfCollection: IMembershipConfMySuffix[] = [
          {
            ...membershipConf,
          },
          { id: 456 },
        ];
        expectedResult = service.addMembershipConfMySuffixToCollectionIfMissing(membershipConfCollection, membershipConf);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MembershipConfMySuffix to an array that doesn't contain it", () => {
        const membershipConf: IMembershipConfMySuffix = { id: 123 };
        const membershipConfCollection: IMembershipConfMySuffix[] = [{ id: 456 }];
        expectedResult = service.addMembershipConfMySuffixToCollectionIfMissing(membershipConfCollection, membershipConf);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(membershipConf);
      });

      it('should add only unique MembershipConfMySuffix to an array', () => {
        const membershipConfArray: IMembershipConfMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 58511 }];
        const membershipConfCollection: IMembershipConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addMembershipConfMySuffixToCollectionIfMissing(membershipConfCollection, ...membershipConfArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const membershipConf: IMembershipConfMySuffix = { id: 123 };
        const membershipConf2: IMembershipConfMySuffix = { id: 456 };
        expectedResult = service.addMembershipConfMySuffixToCollectionIfMissing([], membershipConf, membershipConf2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(membershipConf);
        expect(expectedResult).toContain(membershipConf2);
      });

      it('should accept null and undefined values', () => {
        const membershipConf: IMembershipConfMySuffix = { id: 123 };
        expectedResult = service.addMembershipConfMySuffixToCollectionIfMissing([], null, membershipConf, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(membershipConf);
      });

      it('should return initial array if no MembershipConfMySuffix is added', () => {
        const membershipConfCollection: IMembershipConfMySuffix[] = [{ id: 123 }];
        expectedResult = service.addMembershipConfMySuffixToCollectionIfMissing(membershipConfCollection, undefined, null);
        expect(expectedResult).toEqual(membershipConfCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
