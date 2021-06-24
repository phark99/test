/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LoginHistoryDetailComponent from '@/entities/login-history/login-history-details.vue';
import LoginHistoryClass from '@/entities/login-history/login-history-details.component';
import LoginHistoryService from '@/entities/login-history/login-history.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('LoginHistory Management Detail Component', () => {
    let wrapper: Wrapper<LoginHistoryClass>;
    let comp: LoginHistoryClass;
    let loginHistoryServiceStub: SinonStubbedInstance<LoginHistoryService>;

    beforeEach(() => {
      loginHistoryServiceStub = sinon.createStubInstance<LoginHistoryService>(LoginHistoryService);

      wrapper = shallowMount<LoginHistoryClass>(LoginHistoryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { loginHistoryService: () => loginHistoryServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLoginHistory = { id: 123 };
        loginHistoryServiceStub.find.resolves(foundLoginHistory);

        // WHEN
        comp.retrieveLoginHistory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.loginHistory).toBe(foundLoginHistory);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLoginHistory = { id: 123 };
        loginHistoryServiceStub.find.resolves(foundLoginHistory);

        // WHEN
        comp.beforeRouteEnter({ params: { loginHistoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.loginHistory).toBe(foundLoginHistory);
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
