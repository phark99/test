<template>
  <div>
    <h2 id="page-heading" data-cy="ApiMetaHeading">
      <span v-text="$t('testApp.apiMeta.home.title')" id="api-meta-heading">Api Metas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.apiMeta.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ApiMetaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-api-meta"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.apiMeta.home.createLabel')"> Create a new Api Meta </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && apiMetas && apiMetas.length === 0">
      <span v-text="$t('testApp.apiMeta.home.notFound')">No apiMetas found</span>
    </div>
    <div class="table-responsive" v-if="apiMetas && apiMetas.length > 0">
      <table class="table table-striped" aria-describedby="apiMetas">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('appId')">
              <span v-text="$t('testApp.apiMeta.appId')">App Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'appId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span v-text="$t('testApp.apiMeta.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('value')">
              <span v-text="$t('testApp.apiMeta.value')">Value</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'value'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('testApp.apiMeta.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.apiMeta.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="$t('testApp.apiMeta.modifiedBy')">Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="$t('testApp.apiMeta.modifiedAt')">Modified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('api.id')">
              <span v-text="$t('testApp.apiMeta.api')">Api</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'api.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="apiMeta in apiMetas" :key="apiMeta.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApiMetaView', params: { apiMetaId: apiMeta.id } }">{{ apiMeta.id }}</router-link>
            </td>
            <td>{{ apiMeta.appId }}</td>
            <td>{{ apiMeta.type }}</td>
            <td>{{ apiMeta.value }}</td>
            <td>{{ apiMeta.createdBy }}</td>
            <td>{{ apiMeta.createdAt }}</td>
            <td>{{ apiMeta.modifiedBy }}</td>
            <td>{{ apiMeta.modifiedAt }}</td>
            <td>
              <div v-if="apiMeta.api">
                <router-link :to="{ name: 'ApiView', params: { apiId: apiMeta.api.id } }">{{ apiMeta.api.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ApiMetaView', params: { apiMetaId: apiMeta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ApiMetaEdit', params: { apiMetaId: apiMeta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(apiMeta)"
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
        ><span id="testApp.apiMeta.delete.question" data-cy="apiMetaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-apiMeta-heading" v-text="$t('testApp.apiMeta.delete.question', { id: removeId })">
          Are you sure you want to delete this Api Meta?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-apiMeta"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeApiMeta()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="apiMetas && apiMetas.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./api-meta.component.ts"></script>
