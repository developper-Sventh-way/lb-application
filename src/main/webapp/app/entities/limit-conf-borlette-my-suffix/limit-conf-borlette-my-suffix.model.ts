import dayjs from 'dayjs/esm';
import { IBorletteConfMySuffix } from 'app/entities/borlette-conf-my-suffix/borlette-conf-my-suffix.model';
import { TypeOption } from 'app/entities/enumerations/type-option.model';

export interface ILimitConfBorletteMySuffix {
  id?: number;
  nombreValue?: string | null;
  limit?: number;
  limitStatut?: TypeOption | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  borletteConf?: IBorletteConfMySuffix | null;
}

export class LimitConfBorletteMySuffix implements ILimitConfBorletteMySuffix {
  constructor(
    public id?: number,
    public nombreValue?: string | null,
    public limit?: number,
    public limitStatut?: TypeOption | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public borletteConf?: IBorletteConfMySuffix | null
  ) {}
}

export function getLimitConfBorletteMySuffixIdentifier(limitConfBorlette: ILimitConfBorletteMySuffix): number | undefined {
  return limitConfBorlette.id;
}
