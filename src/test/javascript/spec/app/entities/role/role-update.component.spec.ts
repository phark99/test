/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import RoleUpdateComponent from '@/entities/role/role-update.vue';
import RoleClass from '@/entities/role/role-update.component';
import RoleService from '@/entities/role/role.service';

import UserService from '@/admin/user-management/user-management.service';

import ResourceService from '@/entities/resource/resource.service';

import MenuService from '@/entities/menu/menu.service';

import UserGroupService from '@/entities/user-group/user-group.service';

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
  describe('Role Management Update Component', () => {
    let wrapper: Wrapper<RoleClass>;
    let comp: RoleClass;
    let roleServiceStub: SinonStubbedInstance<RoleService>;

    beforeEach(() => {
      roleServiceStub = sinon.createStubInstance<RoleService>(RoleService);

      wrapper = shallowMount<RoleClass>(RoleUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          roleService: () => roleServiceStub,

          userService: () => new UserService(),

          resourceService: () => new ResourceService(),

          menuService: () => new MenuService(),

          userGroupService: () => new UserGroupService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.role = entity;
        roleServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(roleServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.role = entity;
        roleServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(roleServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRole = { id: 123 };
        roleServiceStub.find.resolves(foundRole);
        roleServiceStub.retrieve.resolves([foundRole]);

        // WHEN
        comp.beforeRouteEnter({ params: { roleId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.role).toBe(foundRole);
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
