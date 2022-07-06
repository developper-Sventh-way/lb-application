import dayjs from 'dayjs/esm';
import { DeviceStatut } from 'app/entities/enumerations/device-statut.model';

export interface IPOSConfigurationMySuffix {
  id?: number;
  pOSName?: string;
  iMEI?: string;
  deviceStatut?: DeviceStatut;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class POSConfigurationMySuffix implements IPOSConfigurationMySuffix {
  constructor(
    public id?: number,
    public pOSName?: string,
    public iMEI?: string,
    public deviceStatut?: DeviceStatut,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getPOSConfigurationMySuffixIdentifier(pOSConfiguration: IPOSConfigurationMySuffix): number | undefined {
  return pOSConfiguration.id;
}
