import { IResourceMeta } from '@/shared/model/resource-meta.model';
import { IRole } from '@/shared/model/role.model';
import { IProject } from '@/shared/model/project.model';

export interface IResource {
  id?: number;
  type?: string;
  name?: string;
  status?: string;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  resourceMetas?: IResourceMeta[] | null;
  roles?: IRole[] | null;
  projectResources?: IProject[] | null;
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public type?: string,
    public name?: string,
    public status?: string,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public resourceMetas?: IResourceMeta[] | null,
    public roles?: IRole[] | null,
    public projectResources?: IProject[] | null
  ) {}
}
