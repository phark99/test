import { IUser } from '@/shared/model/user.model';

export interface ILoginHistory {
  id?: number;
  createdAt?: Date;
  userAgent?: string | null;
  user?: IUser | null;
}

export class LoginHistory implements ILoginHistory {
  constructor(public id?: number, public createdAt?: Date, public userAgent?: string | null, public user?: IUser | null) {}
}
