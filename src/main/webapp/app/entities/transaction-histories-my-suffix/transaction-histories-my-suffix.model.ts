import dayjs from 'dayjs/esm';
import { TransactionType } from 'app/entities/enumerations/transaction-type.model';

export interface ITransactionHistoriesMySuffix {
  id?: number;
  type?: TransactionType;
  description?: string | null;
  montant?: number;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class TransactionHistoriesMySuffix implements ITransactionHistoriesMySuffix {
  constructor(
    public id?: number,
    public type?: TransactionType,
    public description?: string | null,
    public montant?: number,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getTransactionHistoriesMySuffixIdentifier(transactionHistories: ITransactionHistoriesMySuffix): number | undefined {
  return transactionHistories.id;
}
