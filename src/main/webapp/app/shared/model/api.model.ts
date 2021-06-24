import { IApiMeta } from '@/shared/model/api-meta.model';
import { IApiRequest } from '@/shared/model/api-request.model';

export interface IApi {
  id?: number;
  name?: string;
  projectId?: number;
  managerId?: number;
  apiType?: string;
  host?: string | null;
  port?: number | null;
  uri?: string | null;
  version?: string | null;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  apiMetas?: IApiMeta[] | null;
  apiRequests?: IApiRequest[] | null;
}

export class Api implements IApi {
  constructor(
    public id?: number,
    public name?: string,
    public projectId?: number,
    public managerId?: number,
    public apiType?: string,
    public host?: string | null,
    public port?: number | null,
    public uri?: string | null,
    public version?: string | null,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public apiMetas?: IApiMeta[] | null,
    public apiRequests?: IApiRequest[] | null
  ) {}
}
