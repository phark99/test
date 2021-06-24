/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ApiMetaUpdateComponent from '@/entities/api-meta/api-meta-update.vue';
import ApiMetaClass from '@/entities/api-meta/api-meta-update.component';
import ApiMetaService from '@/entities/api-meta/api-meta.service';

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
  describe('ApiMeta Management Update Component', () => {
    let wrapper: Wrapper<ApiMetaClass>;
    let comp: ApiMetaClass;
    let apiMetaServiceStub: SinonStubbedInstance<ApiMetaService>;

    beforeEach(() => {
      apiMetaServiceStub = sinon.createStubInstance<ApiMetaService>(ApiMetaService);

      wrapper = shallowMount<ApiMetaClass>(ApiMetaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          apiMetaService: () => apiMetaServiceStub,

          apiService: () => new ApiService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.apiMeta = entity;
        apiMetaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiMetaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.apiMeta = entity;
        apiMetaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiMetaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApiMeta = { id: 123 };
        apiMetaServiceStub.find.resolves(foundApiMeta);
        apiMetaServiceStub.retrieve.resolves([foundApiMeta]);

        // WHEN
        comp.beforeRouteEnter({ params: { apiMetaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.apiMeta).toBe(foundApiMeta);
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
