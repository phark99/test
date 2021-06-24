/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ApiRequestUpdateComponent from '@/entities/api-request/api-request-update.vue';
import ApiRequestClass from '@/entities/api-request/api-request-update.component';
import ApiRequestService from '@/entities/api-request/api-request.service';

import ApiService from '@/entities/api/api.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('ApiRequest Management Update Component', () => {
    let wrapper: Wrapper<ApiRequestClass>;
    let comp: ApiRequestClass;
    let apiRequestServiceStub: SinonStubbedInstance<ApiRequestService>;

    beforeEach(() => {
      apiRequestServiceStub = sinon.createStubInstance<ApiRequestService>(ApiRequestService);

      wrapper = shallowMount<ApiRequestClass>(ApiRequestUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          apiRequestService: () => apiRequestServiceStub,

          apiService: () => new ApiService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.apiRequest = entity;
        apiRequestServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiRequestServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.apiRequest = entity;
        apiRequestServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiRequestServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApiRequest = { id: 123 };
        apiRequestServiceStub.find.resolves(foundApiRequest);
        apiRequestServiceStub.retrieve.resolves([foundApiRequest]);

        // WHEN
        comp.beforeRouteEnter({ params: { apiRequestId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.apiRequest).toBe(foundApiRequest);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
