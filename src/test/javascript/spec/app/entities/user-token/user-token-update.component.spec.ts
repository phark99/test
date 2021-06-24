/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import UserTokenUpdateComponent from '@/entities/user-token/user-token-update.vue';
import UserTokenClass from '@/entities/user-token/user-token-update.component';
import UserTokenService from '@/entities/user-token/user-token.service';

import UserService from '@/admin/user-management/user-management.service';

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
  describe('UserToken Management Update Component', () => {
    let wrapper: Wrapper<UserTokenClass>;
    let comp: UserTokenClass;
    let userTokenServiceStub: SinonStubbedInstance<UserTokenService>;

    beforeEach(() => {
      userTokenServiceStub = sinon.createStubInstance<UserTokenService>(UserTokenService);

      wrapper = shallowMount<UserTokenClass>(UserTokenUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          userTokenService: () => userTokenServiceStub,

          userService: () => new UserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.userToken = entity;
        userTokenServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userTokenServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userToken = entity;
        userTokenServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userTokenServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserToken = { id: 123 };
        userTokenServiceStub.find.resolves(foundUserToken);
        userTokenServiceStub.retrieve.resolves([foundUserToken]);

        // WHEN
        comp.beforeRouteEnter({ params: { userTokenId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.userToken).toBe(foundUserToken);
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
