/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import ApiRequestService from '@/entities/api-request/api-request.service';
import { ApiRequest } from '@/shared/model/api-request.model';

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
  describe('ApiRequest Service', () => {
    let service: ApiRequestService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ApiRequestService();
      currentDate = new Date();
      elemDefault = new ApiRequest(0, 0, 'AAAAAAA', 0, 'AAAAAAA', 0, currentDate, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
            modifiedAt: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a ApiRequest', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
            modifiedAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a ApiRequest', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a ApiRequest', async () => {
        const returnedFromService = Object.assign(
          {
            apiId: 1,
            clientType: 'BBBBBB',
            projectId: 1,
            description: 'BBBBBB',
            createdBy: 1,
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
            modifiedBy: 1,
            modifiedAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a ApiRequest', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a ApiRequest', async () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
          },
          new ApiRequest()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a ApiRequest', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of ApiRequest', async () => {
        const returnedFromService = Object.assign(
          {
            apiId: 1,
            clientType: 'BBBBBB',
            projectId: 1,
            description: 'BBBBBB',
            createdBy: 1,
            createdAt: dayjs(currentDate).format(DATE_FORMAT),
            modifiedBy: 1,
            modifiedAt: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of ApiRequest', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a ApiRequest', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a ApiRequest', async () => {
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
