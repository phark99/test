/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import UserTokenService from '@/entities/user-token/user-token.service';
import { UserToken } from '@/shared/model/user-token.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('UserToken Service', () => {
    let service: UserTokenService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new UserTokenService();
      currentDate = new Date();
      elemDefault = new UserToken(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            accExpTime: dayjs(currentDate).format(DATE_FORMAT),
            refExpTime: dayjs(currentDate).format(DATE_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a UserToken', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            accExpTime: dayjs(currentDate).format(DATE_FORMAT),
            refExpTime: dayjs(currentDate).format(DATE_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            accExpTime: currentDate,
            refExpTime: currentDate,
            createdAt: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a UserToken', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a UserToken', async () => {
        const returnedFromService = Object.assign(
          {
            accToken: 'BBBBBB',
            accExpTime: dayjs(currentDate).format(DATE_FORMAT),
            refToken: 'BBBBBB',
            refExpTime: dayjs(currentDate).format(DATE_FORMAT),
            createdBy: 1,
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accExpTime: currentDate,
            refExpTime: currentDate,
            createdAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a UserToken', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a UserToken', async () => {
        const patchObject = Object.assign(
          {
            refToken: 'BBBBBB',
            refExpTime: dayjs(currentDate).format(DATE_FORMAT),
            createdBy: 1,
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          new UserToken()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            accExpTime: currentDate,
            refExpTime: currentDate,
            createdAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a UserToken', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of UserToken', async () => {
        const returnedFromService = Object.assign(
          {
            accToken: 'BBBBBB',
            accExpTime: dayjs(currentDate).format(DATE_FORMAT),
            refToken: 'BBBBBB',
            refExpTime: dayjs(currentDate).format(DATE_FORMAT),
            createdBy: 1,
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            accExpTime: currentDate,
            refExpTime: currentDate,
            createdAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of UserToken', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a UserToken', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a UserToken', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
