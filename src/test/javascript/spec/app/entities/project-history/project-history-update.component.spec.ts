/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ProjectHistoryUpdateComponent from '@/entities/project-history/project-history-update.vue';
import ProjectHistoryClass from '@/entities/project-history/project-history-update.component';
import ProjectHistoryService from '@/entities/project-history/project-history.service';

import ProjectService from '@/entities/project/project.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('ProjectHistory Management Update Component', () => {
    let wrapper: Wrapper<ProjectHistoryClass>;
    let comp: ProjectHistoryClass;
    let projectHistoryServiceStub: SinonStubbedInstance<ProjectHistoryService>;

    beforeEach(() => {
      projectHistoryServiceStub = sinon.createStubInstance<ProjectHistoryService>(ProjectHistoryService);

      wrapper = shallowMount<ProjectHistoryClass>(ProjectHistoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          projectHistoryService: () => projectHistoryServiceStub,

          projectService: () => new ProjectService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.projectHistory = entity;
        projectHistoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectHistoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.projectHistory = entity;
        projectHistoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectHistoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProjectHistory = { id: 123 };
        projectHistoryServiceStub.find.resolves(foundProjectHistory);
        projectHistoryServiceStub.retrieve.resolves([foundProjectHistory]);

        // WHEN
        comp.beforeRouteEnter({ params: { projectHistoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.projectHistory).toBe(foundProjectHistory);
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
