<template>
  <div>
    <h2 id="page-heading" data-cy="ResourceHeading">
      <span v-text="$t('testApp.resource.home.title')" id="resource-heading">Resources</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.resource.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ResourceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-resource"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.resource.home.createLabel')"> Create a new Resource </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && resources && resources.length === 0">
      <span v-text="$t('testApp.resource.home.notFound')">No resources found</span>
    </div>
    <div class="table-responsive" v-if="resources && resources.length > 0">
      <table class="table table-striped" aria-describedby="resources">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span v-text="$t('testApp.resource.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('testApp.resource.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('testApp.resource.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('testApp.resource.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.resource.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="$t('testApp.resource.modifiedBy')">Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="$t('testApp.resource.modifiedAt')">Modified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="resource in resources" :key="resource.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ResourceView', params: { resourceId: resource.id } }">{{ resource.id }}</router-link>
            </td>
            <td>{{ resource.type }}</td>
            <td>{{ resource.name }}</td>
            <td>{{ resource.status }}</td>
            <td>{{ resource.createdBy }}</td>
            <td>{{ resource.createdAt }}</td>
            <td>{{ resource.modifiedBy }}</td>
            <td>{{ resource.modifiedAt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ResourceView', params: { resourceId: resource.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ResourceEdit', params: { resourceId: resource.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(resource)"
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
        ><span id="testApp.resource.delete.question" data-cy="resourceDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-resource-heading" v-text="$t('testApp.resource.delete.question', { id: removeId })">
          Are you sure you want to delete this Resource?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-resource"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeResource()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="resources && resources.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./resource.component.ts"></script>
