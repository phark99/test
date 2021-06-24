<template>
  <div>
    <h2 id="page-heading" data-cy="UserTokenHeading">
      <span v-text="$t('testApp.userToken.home.title')" id="user-token-heading">User Tokens</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testApp.userToken.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UserTokenCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-user-token"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testApp.userToken.home.createLabel')"> Create a new User Token </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && userTokens && userTokens.length === 0">
      <span v-text="$t('testApp.userToken.home.notFound')">No userTokens found</span>
    </div>
    <div class="table-responsive" v-if="userTokens && userTokens.length > 0">
      <table class="table table-striped" aria-describedby="userTokens">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('accToken')">
              <span v-text="$t('testApp.userToken.accToken')">Acc Token</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accToken'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('accExpTime')">
              <span v-text="$t('testApp.userToken.accExpTime')">Acc Exp Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accExpTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('refToken')">
              <span v-text="$t('testApp.userToken.refToken')">Ref Token</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'refToken'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('refExpTime')">
              <span v-text="$t('testApp.userToken.refExpTime')">Ref Exp Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'refExpTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('testApp.userToken.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('testApp.userToken.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.id')">
              <span v-text="$t('testApp.userToken.user')">User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="userToken in userTokens" :key="userToken.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UserTokenView', params: { userTokenId: userToken.id } }">{{ userToken.id }}</router-link>
            </td>
            <td>{{ userToken.accToken }}</td>
            <td>{{ userToken.accExpTime }}</td>
            <td>{{ userToken.refToken }}</td>
            <td>{{ userToken.refExpTime }}</td>
            <td>{{ userToken.createdBy }}</td>
            <td>{{ userToken.createdAt }}</td>
            <td>
              {{ userToken.user ? userToken.user.id : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'UserTokenView', params: { userTokenId: userToken.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'UserTokenEdit', params: { userTokenId: userToken.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(userToken)"
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
        ><span id="testApp.userToken.delete.question" data-cy="userTokenDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-userToken-heading" v-text="$t('testApp.userToken.delete.question', { id: removeId })">
          Are you sure you want to delete this User Token?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-userToken"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUserToken()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="userTokens && userTokens.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-token.component.ts"></script>
