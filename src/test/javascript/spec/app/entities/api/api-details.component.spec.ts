/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApiDetailComponent from '@/entities/api/api-details.vue';
import ApiClass from '@/entities/api/api-details.component';
import ApiService from '@/entities/api/api.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Api Management Detail Component', () => {
    let wrapper: Wrapper<ApiClass>;
    let comp: ApiClass;
    let apiServiceStub: SinonStubbedInstance<ApiService>;

    beforeEach(() => {
      apiServiceStub = sinon.createStubInstance<ApiService>(ApiService);

      wrapper = shallowMount<ApiClass>(ApiDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { apiService: () => apiServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApi = { id: 123 };
        apiServiceStub.find.resolves(foundApi);

        // WHEN
        comp.retrieveApi(123);
        await comp.$nextTick();

        // THEN
        expect(comp.api).toBe(foundApi);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApi = { id: 123 };
        apiServiceStub.find.resolves(foundApi);

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
