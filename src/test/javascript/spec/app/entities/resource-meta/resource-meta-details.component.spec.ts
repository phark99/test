/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ResourceMetaDetailComponent from '@/entities/resource-meta/resource-meta-details.vue';
import ResourceMetaClass from '@/entities/resource-meta/resource-meta-details.component';
import ResourceMetaService from '@/entities/resource-meta/resource-meta.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ResourceMeta Management Detail Component', () => {
    let wrapper: Wrapper<ResourceMetaClass>;
    let comp: ResourceMetaClass;
    let resourceMetaServiceStub: SinonStubbedInstance<ResourceMetaService>;

    beforeEach(() => {
      resourceMetaServiceStub = sinon.createStubInstance<ResourceMetaService>(ResourceMetaService);

      wrapper = shallowMount<ResourceMetaClass>(ResourceMetaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { resourceMetaService: () => resourceMetaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundResourceMeta = { id: 123 };
        resourceMetaServiceStub.find.resolves(foundResourceMeta);

        // WHEN
        comp.retrieveResourceMeta(123);
        await comp.$nextTick();

        // THEN
        expect(comp.resourceMeta).toBe(foundResourceMeta);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundResourceMeta = { id: 123 };
        resourceMetaServiceStub.find.resolves(foundResourceMeta);

        // WHEN
        comp.beforeRouteEnter({ params: { resourceMetaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.resourceMeta).toBe(foundResourceMeta);
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
