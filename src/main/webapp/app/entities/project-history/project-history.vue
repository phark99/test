<template>
  <div>
    <h2 id="page-heading" data-cy="ProjectHistoryHeading">
      <span v-text="$t('testApp.projectHistory.home.title')" id="project-history-heading">Project Histories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.projectHistory.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProjectHistoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-project-history"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.projectHistory.home.createLabel')"> Create a new Project History </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && projectHistories && projectHistories.length === 0">
      <span v-text="$t('testApp.projectHistory.home.notFound')">No projectHistories found</span>
    </div>
    <div class="table-responsive" v-if="projectHistories && projectHistories.length > 0">
      <table class="table table-striped" aria-describedby="projectHistories">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('testApp.projectHistory.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('testApp.projectHistory.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deptCode')">
              <span v-text="$t('testApp.projectHistory.deptCode')">Dept Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deptCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tags')">
              <span v-text="$t('testApp.projectHistory.tags')">Tags</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tags'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('testApp.projectHistory.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainAdminId')">
              <span v-text="$t('testApp.projectHistory.mainAdminId')">Main Admin Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainAdminId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('testApp.projectHistory.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.projectHistory.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('project.id')">
              <span v-text="$t('testApp.projectHistory.project')">Project</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'project.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="projectHistory in projectHistories" :key="projectHistory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProjectHistoryView', params: { projectHistoryId: projectHistory.id } }">{{
                projectHistory.id
              }}</router-link>
            </td>
            <td>{{ projectHistory.name }}</td>
            <td>{{ projectHistory.description }}</td>
            <td>{{ projectHistory.deptCode }}</td>
            <td>{{ projectHistory.tags }}</td>
            <td>{{ projectHistory.status }}</td>
            <td>{{ projectHistory.mainAdminId }}</td>
            <td>{{ projectHistory.createdBy }}</td>
            <td>{{ projectHistory.createdAt }}</td>
            <td>
              <div v-if="projectHistory.project">
                <router-link :to="{ name: 'ProjectView', params: { projectId: projectHistory.project.id } }">{{
                  projectHistory.project.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ProjectHistoryView', params: { projectHistoryId: projectHistory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ProjectHistoryEdit', params: { projectHistoryId: projectHistory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(projectHistory)"
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
        ><span id="testApp.projectHistory.delete.question" data-cy="projectHistoryDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-projectHistory-heading" v-text="$t('testApp.projectHistory.delete.question', { id: removeId })">
          Are you sure you want to delete this Project History?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-projectHistory"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProjectHistory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="projectHistories && projectHistories.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./project-history.component.ts"></script>
