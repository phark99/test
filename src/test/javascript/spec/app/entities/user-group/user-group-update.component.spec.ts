/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import UserGroupUpdateComponent from '@/entities/user-group/user-group-update.vue';
import UserGroupClass from '@/entities/user-group/user-group-update.component';
import UserGroupService from '@/entities/user-group/user-group.service';

import UserService from '@/admin/user-management/user-management.service';

import RoleService from '@/entities/role/role.service';

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
  describe('UserGroup Management Update Component', () => {
    let wrapper: Wrapper<UserGroupClass>;
    let comp: UserGroupClass;
    let userGroupServiceStub: SinonStubbedInstance<UserGroupService>;

    beforeEach(() => {
      userGroupServiceStub = sinon.createStubInstance<UserGroupService>(UserGroupService);

      wrapper = shallowMount<UserGroupClass>(UserGroupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          userGroupService: () => userGroupServiceStub,

          userService: () => new UserService(),

          roleService: () => new RoleService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.userGroup = entity;
        userGroupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userGroupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userGroup = entity;
        userGroupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userGroupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserGroup = { id: 123 };
        userGroupServiceStub.find.resolves(foundUserGroup);
        userGroupServiceStub.retrieve.resolves([foundUserGroup]);

        // WHEN
        comp.beforeRouteEnter({ params: { userGroupId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.userGroup).toBe(foundUserGroup);
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
