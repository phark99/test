/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ResourceUpdateComponent from '@/entities/resource/resource-update.vue';
import ResourceClass from '@/entities/resource/resource-update.component';
import ResourceService from '@/entities/resource/resource.service';

import ResourceMetaService from '@/entities/resource-meta/resource-meta.service';

import RoleService from '@/entities/role/role.service';

import ProjectService from '@/entities/project/project.service';

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
  describe('Resource Management Update Component', () => {
    let wrapper: Wrapper<ResourceClass>;
    let comp: ResourceClass;
    let resourceServiceStub: SinonStubbedInstance<ResourceService>;

    beforeEach(() => {
      resourceServiceStub = sinon.createStubInstance<ResourceService>(ResourceService);

      wrapper = shallowMount<ResourceClass>(ResourceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          resourceService: () => resourceServiceStub,

          resourceMetaService: () => new ResourceMetaService(),

          roleService: () => new RoleService(),

          projectService: () => new ProjectService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.resource = entity;
        resourceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resourceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.resource = entity;
        resourceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resourceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundResource = { id: 123 };
        resourceServiceStub.find.resolves(foundResource);
        resourceServiceStub.retrieve.resolves([foundResource]);

        // WHEN
        comp.beforeRouteEnter({ params: { resourceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.resource).toBe(foundResource);
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
