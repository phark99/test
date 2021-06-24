import { IResource } from '@/shared/model/resource.model';
import { IProjectHistory } from '@/shared/model/project-history.model';

export interface IProject {
  id?: number;
  name?: string | null;
  description?: string | null;
  deptCode?: string | null;
  tags?: string | null;
  status?: string | null;
  mainAdminId?: number | null;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  resources?: IResource[] | null;
  projectHistories?: IProjectHistory[] | null;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public deptCode?: string | null,
    public tags?: string | null,
    public status?: string | null,
    public mainAdminId?: number | null,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public resources?: IResource[] | null,
    public projectHistories?: IProjectHistory[] | null
  ) {}
}
