/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LoginHistoryComponent from '@/entities/login-history/login-history.vue';
import LoginHistoryClass from '@/entities/login-history/login-history.component';
import LoginHistoryService from '@/entities/login-history/login-history.service';

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
  describe('LoginHistory Management Component', () => {
    let wrapper: Wrapper<LoginHistoryClass>;
    let comp: LoginHistoryClass;
    let loginHistoryServiceStub: SinonStubbedInstance<LoginHistoryService>;

    beforeEach(() => {
      loginHistoryServiceStub = sinon.createStubInstance<LoginHistoryService>(LoginHistoryService);
      loginHistoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LoginHistoryClass>(LoginHistoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          loginHistoryService: () => loginHistoryServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      loginHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLoginHistorys();
      await comp.$nextTick();

      // THEN
      expect(loginHistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.loginHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      loginHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(loginHistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.loginHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      loginHistoryServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(loginHistoryServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      loginHistoryServiceStub.retrieve.reset();
      loginHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(loginHistoryServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.loginHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      loginHistoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLoginHistory();
      await comp.$nextTick();

      // THEN
      expect(loginHistoryServiceStub.delete.called).toBeTruthy();
      expect(loginHistoryServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
