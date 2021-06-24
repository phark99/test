/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApiMetaDetailComponent from '@/entities/api-meta/api-meta-details.vue';
import ApiMetaClass from '@/entities/api-meta/api-meta-details.component';
import ApiMetaService from '@/entities/api-meta/api-meta.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ApiMeta Management Detail Component', () => {
    let wrapper: Wrapper<ApiMetaClass>;
    let comp: ApiMetaClass;
    let apiMetaServiceStub: SinonStubbedInstance<ApiMetaService>;

    beforeEach(() => {
      apiMetaServiceStub = sinon.createStubInstance<ApiMetaService>(ApiMetaService);

      wrapper = shallowMount<ApiMetaClass>(ApiMetaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { apiMetaService: () => apiMetaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApiMeta = { id: 123 };
        apiMetaServiceStub.find.resolves(foundApiMeta);

        // WHEN
        comp.retrieveApiMeta(123);
        await comp.$nextTick();

        // THEN
        expect(comp.apiMeta).toBe(foundApiMeta);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApiMeta = { id: 123 };
        apiMetaServiceStub.find.resolves(foundApiMeta);

        // WHEN
        comp.beforeRouteEnter({ params: { apiMetaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.apiMeta).toBe(foundApiMeta);
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
