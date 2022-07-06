import dayjs from 'dayjs/esm';
import { ITicketOptionMySuffix } from 'app/entities/ticket-option-my-suffix/ticket-option-my-suffix.model';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { ITirageMySuffix } from 'app/entities/tirage-my-suffix/tirage-my-suffix.model';
import { IUserSaleAccountMySuffix } from 'app/entities/user-sale-account-my-suffix/user-sale-account-my-suffix.model';
import { StatutFiche } from 'app/entities/enumerations/statut-fiche.model';

export interface ITicketMySuffix {
  id?: number;
  ticketNo?: string | null;
  statutFiche?: StatutFiche;
  closeBy?: string | null;
  closeDate?: dayjs.Dayjs | null;
  isClosed?: boolean | null;
  closeById?: number | null;
  payBy?: string | null;
  payDate?: dayjs.Dayjs | null;
  isPay?: boolean | null;
  payById?: number | null;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  ticketOptions?: ITicketOptionMySuffix[] | null;
  pointOfSale?: IPointOfSaleMySuffix | null;
  tirage?: ITirageMySuffix | null;
  userSaleAccount?: IUserSaleAccountMySuffix | null;
}

export class TicketMySuffix implements ITicketMySuffix {
  constructor(
    public id?: number,
    public ticketNo?: string | null,
    public statutFiche?: StatutFiche,
    public closeBy?: string | null,
    public closeDate?: dayjs.Dayjs | null,
    public isClosed?: boolean | null,
    public closeById?: number | null,
    public payBy?: string | null,
    public payDate?: dayjs.Dayjs | null,
    public isPay?: boolean | null,
    public payById?: number | null,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public ticketOptions?: ITicketOptionMySuffix[] | null,
    public pointOfSale?: IPointOfSaleMySuffix | null,
    public tirage?: ITirageMySuffix | null,
    public userSaleAccount?: IUserSaleAccountMySuffix | null
  ) {
    this.isClosed = this.isClosed ?? false;
    this.isPay = this.isPay ?? false;
  }
}

export function getTicketMySuffixIdentifier(ticket: ITicketMySuffix): number | undefined {
  return ticket.id;
}
