/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ResourceMetaUpdateComponent from '@/entities/resource-meta/resource-meta-update.vue';
import ResourceMetaClass from '@/entities/resource-meta/resource-meta-update.component';
import ResourceMetaService from '@/entities/resource-meta/resource-meta.service';

import ResourceService from '@/entities/resource/resource.service';

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
  describe('ResourceMeta Management Update Component', () => {
    let wrapper: Wrapper<ResourceMetaClass>;
    let comp: ResourceMetaClass;
    let resourceMetaServiceStub: SinonStubbedInstance<ResourceMetaService>;

    beforeEach(() => {
      resourceMetaServiceStub = sinon.createStubInstance<ResourceMetaService>(ResourceMetaService);

      wrapper = shallowMount<ResourceMetaClass>(ResourceMetaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          resourceMetaService: () => resourceMetaServiceStub,

          resourceService: () => new ResourceService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.resourceMeta = entity;
        resourceMetaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resourceMetaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.resourceMeta = entity;
        resourceMetaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resourceMetaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundResourceMeta = { id: 123 };
        resourceMetaServiceStub.find.resolves(foundResourceMeta);
        resourceMetaServiceStub.retrieve.resolves([foundResourceMeta]);

        // WHEN
        comp.beforeRouteEnter({ params: { resourceMetaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.resourceMeta).toBe(foundResourceMeta);
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
