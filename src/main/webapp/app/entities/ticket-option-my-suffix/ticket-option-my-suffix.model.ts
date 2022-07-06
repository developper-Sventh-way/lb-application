import dayjs from 'dayjs/esm';
import { ITicketMySuffix } from 'app/entities/ticket-my-suffix/ticket-my-suffix.model';
import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { StatutFiche } from 'app/entities/enumerations/statut-fiche.model';

export interface ITicketOptionMySuffix {
  id?: number;
  contenu?: string | null;
  playAmount?: number;
  typeOption?: TypeOption;
  statutOption?: StatutFiche;
  multiplicateur?: number;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  ticket?: ITicketMySuffix | null;
}

export class TicketOptionMySuffix implements ITicketOptionMySuffix {
  constructor(
    public id?: number,
    public contenu?: string | null,
    public playAmount?: number,
    public typeOption?: TypeOption,
    public statutOption?: StatutFiche,
    public multiplicateur?: number,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public ticket?: ITicketMySuffix | null
  ) {}
}

export function getTicketOptionMySuffixIdentifier(ticketOption: ITicketOptionMySuffix): number | undefined {
  return ticketOption.id;
}
