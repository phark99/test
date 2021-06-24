import { IProject } from '@/shared/model/project.model';

export interface IProjectHistory {
  id?: number;
  name?: string | null;
  description?: string | null;
  deptCode?: string | null;
  tags?: string | null;
  status?: string | null;
  mainAdminId?: number | null;
  createdBy?: number;
  createdAt?: Date;
  project?: IProject | null;
}

export class ProjectHistory implements IProjectHistory {
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
    public project?: IProject | null
  ) {}
}
