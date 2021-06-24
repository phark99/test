<template>
  <div>
    <h2 id="page-heading" data-cy="LoginHistoryHeading">
      <span v-text="$t('testApp.loginHistory.home.title')" id="login-history-heading">Login Histories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.loginHistory.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LoginHistoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-login-history"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.loginHistory.home.createLabel')"> Create a new Login History </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && loginHistories && loginHistories.length === 0">
      <span v-text="$t('testApp.loginHistory.home.notFound')">No loginHistories found</span>
    </div>
    <div class="table-responsive" v-if="loginHistories && loginHistories.length > 0">
      <table class="table table-striped" aria-describedby="loginHistories">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.loginHistory.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('userAgent')">
              <span v-text="$t('testApp.loginHistory.userAgent')">User Agent</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userAgent'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.id')">
              <span v-text="$t('testApp.loginHistory.user')">User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="loginHistory in loginHistories" :key="loginHistory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LoginHistoryView', params: { loginHistoryId: loginHistory.id } }">{{
                loginHistory.id
              }}</router-link>
            </td>
            <td>{{ loginHistory.createdAt }}</td>
            <td>{{ loginHistory.userAgent }}</td>
            <td>
              {{ loginHistory.user ? loginHistory.user.id : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LoginHistoryView', params: { loginHistoryId: loginHistory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LoginHistoryEdit', params: { loginHistoryId: loginHistory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(loginHistory)"
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
        ><span id="testApp.loginHistory.delete.question" data-cy="loginHistoryDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-loginHistory-heading" v-text="$t('testApp.loginHistory.delete.question', { id: removeId })">
          Are you sure you want to delete this Login History?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-loginHistory"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLoginHistory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="loginHistories && loginHistories.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./login-history.component.ts"></script>
