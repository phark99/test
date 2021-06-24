import { IApi } from '@/shared/model/api.model';

export interface IApiRequest {
  id?: number;
  apiId?: number;
  clientType?: string;
  projectId?: number;
  description?: string | null;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  apis?: IApi[] | null;
}

export class ApiRequest implements IApiRequest {
  constructor(
    public id?: number,
    public apiId?: number,
    public clientType?: string,
    public projectId?: number,
    public description?: string | null,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public apis?: IApi[] | null
  ) {}
}
