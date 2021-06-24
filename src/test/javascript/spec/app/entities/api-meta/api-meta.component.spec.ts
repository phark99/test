/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ApiMetaComponent from '@/entities/api-meta/api-meta.vue';
import ApiMetaClass from '@/entities/api-meta/api-meta.component';
import ApiMetaService from '@/entities/api-meta/api-meta.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ApiMeta Management Component', () => {
    let wrapper: Wrapper<ApiMetaClass>;
    let comp: ApiMetaClass;
    let apiMetaServiceStub: SinonStubbedInstance<ApiMetaService>;

    beforeEach(() => {
      apiMetaServiceStub = sinon.createStubInstance<ApiMetaService>(ApiMetaService);
      apiMetaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ApiMetaClass>(ApiMetaComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          apiMetaService: () => apiMetaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      apiMetaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllApiMetas();
      await comp.$nextTick();

      // THEN
      expect(apiMetaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apiMetas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      apiMetaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(apiMetaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apiMetas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      apiMetaServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(apiMetaServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      apiMetaServiceStub.retrieve.reset();
      apiMetaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(apiMetaServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.apiMetas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      apiMetaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeApiMeta();
      await comp.$nextTick();

      // THEN
      expect(apiMetaServiceStub.delete.called).toBeTruthy();
      expect(apiMetaServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
