/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UserTokenDetailComponent from '@/entities/user-token/user-token-details.vue';
import UserTokenClass from '@/entities/user-token/user-token-details.component';
import UserTokenService from '@/entities/user-token/user-token.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserToken Management Detail Component', () => {
    let wrapper: Wrapper<UserTokenClass>;
    let comp: UserTokenClass;
    let userTokenServiceStub: SinonStubbedInstance<UserTokenService>;

    beforeEach(() => {
      userTokenServiceStub = sinon.createStubInstance<UserTokenService>(UserTokenService);

      wrapper = shallowMount<UserTokenClass>(UserTokenDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { userTokenService: () => userTokenServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserToken = { id: 123 };
        userTokenServiceStub.find.resolves(foundUserToken);

        // WHEN
        comp.retrieveUserToken(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userToken).toBe(foundUserToken);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserToken = { id: 123 };
        userTokenServiceStub.find.resolves(foundUserToken);

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
