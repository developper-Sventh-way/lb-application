import dayjs from 'dayjs/esm';
import { ITicketMySuffix } from 'app/entities/ticket-my-suffix/ticket-my-suffix.model';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

export interface IUserSaleAccountMySuffix {
  id?: number;
  balance?: number;
  lastPayment?: number;
  statut?: UserSaleAccountStatut | null;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  tickets?: ITicketMySuffix[] | null;
  utilisateur?: IUtilisateurMySuffix | null;
}

export class UserSaleAccountMySuffix implements IUserSaleAccountMySuffix {
  constructor(
    public id?: number,
    public balance?: number,
    public lastPayment?: number,
    public statut?: UserSaleAccountStatut | null,
    public startDate?: dayjs.Dayjs,
    public endDate?: dayjs.Dayjs,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public tickets?: ITicketMySuffix[] | null,
    public utilisateur?: IUtilisateurMySuffix | null
  ) {}
}

export function getUserSaleAccountMySuffixIdentifier(userSaleAccount: IUserSaleAccountMySuffix): number | undefined {
  return userSaleAccount.id;
}
