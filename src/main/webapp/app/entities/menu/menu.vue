<template>
  <div>
    <h2 id="page-heading" data-cy="MenuHeading">
      <span v-text="$t('testApp.menu.home.title')" id="menu-heading">Menus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.menu.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MenuCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-menu">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.menu.home.createLabel')"> Create a new Menu </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && menus && menus.length === 0">
      <span v-text="$t('testApp.menu.home.notFound')">No menus found</span>
    </div>
    <div class="table-responsive" v-if="menus && menus.length > 0">
      <table class="table table-striped" aria-describedby="menus">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('testApp.menu.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('testApp.menu.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('depth')">
              <span v-text="$t('testApp.menu.depth')">Depth</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'depth'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dispOrder')">
              <span v-text="$t('testApp.menu.dispOrder')">Disp Order</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dispOrder'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dispYn')">
              <span v-text="$t('testApp.menu.dispYn')">Disp Yn</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dispYn'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('uriPath')">
              <span v-text="$t('testApp.menu.uriPath')">Uri Path</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'uriPath'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('parentId')">
              <span v-text="$t('testApp.menu.parentId')">Parent Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parentId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('testApp.menu.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.menu.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="$t('testApp.menu.modifiedBy')">Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="$t('testApp.menu.modifiedAt')">Modified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="menu in menus" :key="menu.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MenuView', params: { menuId: menu.id } }">{{ menu.id }}</router-link>
            </td>
            <td>{{ menu.name }}</td>
            <td>{{ menu.description }}</td>
            <td>{{ menu.depth }}</td>
            <td>{{ menu.dispOrder }}</td>
            <td>{{ menu.dispYn }}</td>
            <td>{{ menu.uriPath }}</td>
            <td>{{ menu.parentId }}</td>
            <td>{{ menu.createdBy }}</td>
            <td>{{ menu.createdAt }}</td>
            <td>{{ menu.modifiedBy }}</td>
            <td>{{ menu.modifiedAt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MenuView', params: { menuId: menu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MenuEdit', params: { menuId: menu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(menu)"
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
        ><span id="testApp.menu.delete.question" data-cy="menuDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-menu-heading" v-text="$t('testApp.menu.delete.question', { id: removeId })">
          Are you sure you want to delete this Menu?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-menu"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMenu()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="menus && menus.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./menu.component.ts"></script>
