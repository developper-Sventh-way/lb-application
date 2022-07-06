import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { TypeOption } from 'app/entities/enumerations/type-option.model';

export interface ILimitConfPointMySuffix {
  id?: number;
  nombreValue?: string | null;
  limit?: number;
  limitStatut?: TypeOption | null;
  pointOfSale?: IPointOfSaleMySuffix | null;
}

export class LimitConfPointMySuffix implements ILimitConfPointMySuffix {
  constructor(
    public id?: number,
    public nombreValue?: string | null,
    public limit?: number,
    public limitStatut?: TypeOption | null,
    public pointOfSale?: IPointOfSaleMySuffix | null
  ) {}
}

export function getLimitConfPointMySuffixIdentifier(limitConfPoint: ILimitConfPointMySuffix): number | undefined {
  return limitConfPoint.id;
}
