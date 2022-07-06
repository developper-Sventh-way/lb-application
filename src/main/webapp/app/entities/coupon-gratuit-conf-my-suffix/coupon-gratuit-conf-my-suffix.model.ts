import dayjs from 'dayjs/esm';
import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';

export interface ICouponGratuitConfMySuffix {
  id?: number;
  typeOption?: TypeOption;
  maximumCount?: number;
  obstinateAmount?: number;
  statut?: UserStatut;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class CouponGratuitConfMySuffix implements ICouponGratuitConfMySuffix {
  constructor(
    public id?: number,
    public typeOption?: TypeOption,
    public maximumCount?: number,
    public obstinateAmount?: number,
    public statut?: UserStatut,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getCouponGratuitConfMySuffixIdentifier(couponGratuitConf: ICouponGratuitConfMySuffix): number | undefined {
  return couponGratuitConf.id;
}
