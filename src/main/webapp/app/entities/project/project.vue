<template>
  <div>
    <h2 id="page-heading" data-cy="ProjectHeading">
      <span v-text="$t('testApp.project.home.title')" id="project-heading">Projects</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.project.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProjectCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-project"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.project.home.createLabel')"> Create a new Project </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && projects && projects.length === 0">
      <span v-text="$t('testApp.project.home.notFound')">No projects found</span>
    </div>
    <div class="table-responsive" v-if="projects && projects.length > 0">
      <table class="table table-striped" aria-describedby="projects">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('testApp.project.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('testApp.project.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deptCode')">
              <span v-text="$t('testApp.project.deptCode')">Dept Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deptCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tags')">
              <span v-text="$t('testApp.project.tags')">Tags</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tags'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('testApp.project.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainAdminId')">
              <span v-text="$t('testApp.project.mainAdminId')">Main Admin Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainAdminId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('testApp.project.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.project.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="$t('testApp.project.modifiedBy')">Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="$t('testApp.project.modifiedAt')">Modified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="project in projects" :key="project.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProjectView', params: { projectId: project.id } }">{{ project.id }}</router-link>
            </td>
            <td>{{ project.name }}</td>
            <td>{{ project.description }}</td>
            <td>{{ project.deptCode }}</td>
            <td>{{ project.tags }}</td>
            <td>{{ project.status }}</td>
            <td>{{ project.mainAdminId }}</td>
            <td>{{ project.createdBy }}</td>
            <td>{{ project.createdAt }}</td>
            <td>{{ project.modifiedBy }}</td>
            <td>{{ project.modifiedAt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProjectView', params: { projectId: project.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProjectEdit', params: { projectId: project.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(project)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="testApp.project.delete.question" data-cy="projectDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-project-heading" v-text="$t('testApp.project.delete.question', { id: removeId })">
          Are you sure you want to delete this Project?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-project"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProject()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="projects && projects.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./project.component.ts"></script>
