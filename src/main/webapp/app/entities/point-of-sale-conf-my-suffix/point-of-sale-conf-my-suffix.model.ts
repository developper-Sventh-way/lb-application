import dayjs from 'dayjs/esm';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';

export interface IPointOfSaleConfMySuffix {
  id?: number;
  pourcentageCaissier?: number | null;
  pourcentageResponsable?: number | null;
  startTime?: string | null;
  endTime?: string | null;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  pointOfSale?: IPointOfSaleMySuffix | null;
}

export class PointOfSaleConfMySuffix implements IPointOfSaleConfMySuffix {
  constructor(
    public id?: number,
    public pourcentageCaissier?: number | null,
    public pourcentageResponsable?: number | null,
    public startTime?: string | null,
    public endTime?: string | null,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public pointOfSale?: IPointOfSaleMySuffix | null
  ) {}
}

export function getPointOfSaleConfMySuffixIdentifier(pointOfSaleConf: IPointOfSaleConfMySuffix): number | undefined {
  return pointOfSaleConf.id;
}
