/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ApiRequestComponent from '@/entities/api-request/api-request.vue';
import ApiRequestClass from '@/entities/api-request/api-request.component';
import ApiRequestService from '@/entities/api-request/api-request.service';

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
  describe('ApiRequest Management Component', () => {
    let wrapper: Wrapper<ApiRequestClass>;
    let comp: ApiRequestClass;
    let apiRequestServiceStub: SinonStubbedInstance<ApiRequestService>;

    beforeEach(() => {
      apiRequestServiceStub = sinon.createStubInstance<ApiRequestService>(ApiRequestService);
      apiRequestServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ApiRequestClass>(ApiRequestComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          apiRequestService: () => apiRequestServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      apiRequestServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllApiRequests();
      await comp.$nextTick();

      // THEN
      expect(apiRequestServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apiRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      apiRequestServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(apiRequestServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apiRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      apiRequestServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(apiRequestServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      apiRequestServiceStub.retrieve.reset();
      apiRequestServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(apiRequestServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.apiRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      apiRequestServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeApiRequest();
      await comp.$nextTick();

      // THEN
      expect(apiRequestServiceStub.delete.called).toBeTruthy();
      expect(apiRequestServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
