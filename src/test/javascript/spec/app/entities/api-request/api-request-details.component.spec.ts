/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApiRequestDetailComponent from '@/entities/api-request/api-request-details.vue';
import ApiRequestClass from '@/entities/api-request/api-request-details.component';
import ApiRequestService from '@/entities/api-request/api-request.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ApiRequest Management Detail Component', () => {
    let wrapper: Wrapper<ApiRequestClass>;
    let comp: ApiRequestClass;
    let apiRequestServiceStub: SinonStubbedInstance<ApiRequestService>;

    beforeEach(() => {
      apiRequestServiceStub = sinon.createStubInstance<ApiRequestService>(ApiRequestService);

      wrapper = shallowMount<ApiRequestClass>(ApiRequestDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { apiRequestService: () => apiRequestServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApiRequest = { id: 123 };
        apiRequestServiceStub.find.resolves(foundApiRequest);

        // WHEN
        comp.retrieveApiRequest(123);
        await comp.$nextTick();

        // THEN
        expect(comp.apiRequest).toBe(foundApiRequest);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApiRequest = { id: 123 };
        apiRequestServiceStub.find.resolves(foundApiRequest);

        // WHEN
        comp.beforeRouteEnter({ params: { apiRequestId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.apiRequest).toBe(foundApiRequest);
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
