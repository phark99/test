<template>
  <div>
    <h2 id="page-heading" data-cy="ApiRequestHeading">
      <span v-text="$t('testApp.apiRequest.home.title')" id="api-request-heading">Api Requests</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.apiRequest.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ApiRequestCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-api-request"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.apiRequest.home.createLabel')"> Create a new Api Request </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && apiRequests && apiRequests.length === 0">
      <span v-text="$t('testApp.apiRequest.home.notFound')">No apiRequests found</span>
    </div>
    <div class="table-responsive" v-if="apiRequests && apiRequests.length > 0">
      <table class="table table-striped" aria-describedby="apiRequests">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('apiId')">
              <span v-text="$t('testApp.apiRequest.apiId')">Api Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'apiId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('clientType')">
              <span v-text="$t('testApp.apiRequest.clientType')">Client Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'clientType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('projectId')">
              <span v-text="$t('testApp.apiRequest.projectId')">Project Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'projectId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('testApp.apiRequest.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('testApp.apiRequest.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.apiRequest.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="$t('testApp.apiRequest.modifiedBy')">Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="$t('testApp.apiRequest.modifiedAt')">Modified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="apiRequest in apiRequests" :key="apiRequest.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApiRequestView', params: { apiRequestId: apiRequest.id } }">{{ apiRequest.id }}</router-link>
            </td>
            <td>{{ apiRequest.apiId }}</td>
            <td>{{ apiRequest.clientType }}</td>
            <td>{{ apiRequest.projectId }}</td>
            <td>{{ apiRequest.description }}</td>
            <td>{{ apiRequest.createdBy }}</td>
            <td>{{ apiRequest.createdAt }}</td>
            <td>{{ apiRequest.modifiedBy }}</td>
            <td>{{ apiRequest.modifiedAt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ApiRequestView', params: { apiRequestId: apiRequest.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ApiRequestEdit', params: { apiRequestId: apiRequest.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(apiRequest)"
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
        ><span id="testApp.apiRequest.delete.question" data-cy="apiRequestDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-apiRequest-heading" v-text="$t('testApp.apiRequest.delete.question', { id: removeId })">
          Are you sure you want to delete this Api Request?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-apiRequest"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeApiRequest()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="apiRequests && apiRequests.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./api-request.component.ts"></script>
