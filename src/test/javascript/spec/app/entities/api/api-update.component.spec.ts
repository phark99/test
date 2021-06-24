/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ApiUpdateComponent from '@/entities/api/api-update.vue';
import ApiClass from '@/entities/api/api-update.component';
import ApiService from '@/entities/api/api.service';

import ApiMetaService from '@/entities/api-meta/api-meta.service';

import ApiRequestService from '@/entities/api-request/api-request.service';

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
  describe('Api Management Update Component', () => {
    let wrapper: Wrapper<ApiClass>;
    let comp: ApiClass;
    let apiServiceStub: SinonStubbedInstance<ApiService>;

    beforeEach(() => {
      apiServiceStub = sinon.createStubInstance<ApiService>(ApiService);

      wrapper = shallowMount<ApiClass>(ApiUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          apiService: () => apiServiceStub,

          apiMetaService: () => new ApiMetaService(),

          apiRequestService: () => new ApiRequestService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.api = entity;
        apiServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.api = entity;
        apiServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApi = { id: 123 };
        apiServiceStub.find.resolves(foundApi);
        apiServiceStub.retrieve.resolves([foundApi]);

        // WHEN
        comp.beforeRouteEnter({ params: { apiId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.api).toBe(foundApi);
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
