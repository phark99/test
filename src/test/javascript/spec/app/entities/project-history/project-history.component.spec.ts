/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProjectHistoryComponent from '@/entities/project-history/project-history.vue';
import ProjectHistoryClass from '@/entities/project-history/project-history.component';
import ProjectHistoryService from '@/entities/project-history/project-history.service';

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
  describe('ProjectHistory Management Component', () => {
    let wrapper: Wrapper<ProjectHistoryClass>;
    let comp: ProjectHistoryClass;
    let projectHistoryServiceStub: SinonStubbedInstance<ProjectHistoryService>;

    beforeEach(() => {
      projectHistoryServiceStub = sinon.createStubInstance<ProjectHistoryService>(ProjectHistoryService);
      projectHistoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProjectHistoryClass>(ProjectHistoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          projectHistoryService: () => projectHistoryServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      projectHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProjectHistorys();
      await comp.$nextTick();

      // THEN
      expect(projectHistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.projectHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      projectHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(projectHistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.projectHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      projectHistoryServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(projectHistoryServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      projectHistoryServiceStub.retrieve.reset();
      projectHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(projectHistoryServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.projectHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      projectHistoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeProjectHistory();
      await comp.$nextTick();

      // THEN
      expect(projectHistoryServiceStub.delete.called).toBeTruthy();
      expect(projectHistoryServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
