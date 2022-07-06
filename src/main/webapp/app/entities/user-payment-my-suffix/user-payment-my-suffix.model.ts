import dayjs from 'dayjs/esm';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';

export interface IUserPaymentMySuffix {
  id?: number;
  payAmount?: number;
  balance?: number;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  utilisateur?: IUtilisateurMySuffix | null;
}

export class UserPaymentMySuffix implements IUserPaymentMySuffix {
  constructor(
    public id?: number,
    public payAmount?: number,
    public balance?: number,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public utilisateur?: IUtilisateurMySuffix | null
  ) {}
}

export function getUserPaymentMySuffixIdentifier(userPayment: IUserPaymentMySuffix): number | undefined {
  return userPayment.id;
}
