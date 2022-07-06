import dayjs from 'dayjs/esm';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UserPaymentType } from 'app/entities/enumerations/user-payment-type.model';

export interface IUserPaymentConfMySuffix {
  id?: number;
  type?: UserPaymentType;
  total?: number;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  utilisateur?: IUtilisateurMySuffix | null;
}

export class UserPaymentConfMySuffix implements IUserPaymentConfMySuffix {
  constructor(
    public id?: number,
    public type?: UserPaymentType,
    public total?: number,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public utilisateur?: IUtilisateurMySuffix | null
  ) {}
}

export function getUserPaymentConfMySuffixIdentifier(userPaymentConf: IUserPaymentConfMySuffix): number | undefined {
  return userPaymentConf.id;
}
