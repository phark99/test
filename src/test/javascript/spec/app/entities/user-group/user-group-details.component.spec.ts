/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UserGroupDetailComponent from '@/entities/user-group/user-group-details.vue';
import UserGroupClass from '@/entities/user-group/user-group-details.component';
import UserGroupService from '@/entities/user-group/user-group.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserGroup Management Detail Component', () => {
    let wrapper: Wrapper<UserGroupClass>;
    let comp: UserGroupClass;
    let userGroupServiceStub: SinonStubbedInstance<UserGroupService>;

    beforeEach(() => {
      userGroupServiceStub = sinon.createStubInstance<UserGroupService>(UserGroupService);

      wrapper = shallowMount<UserGroupClass>(UserGroupDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { userGroupService: () => userGroupServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserGroup = { id: 123 };
        userGroupServiceStub.find.resolves(foundUserGroup);

        // WHEN
        comp.retrieveUserGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userGroup).toBe(foundUserGroup);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserGroup = { id: 123 };
        userGroupServiceStub.find.resolves(foundUserGroup);

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
