<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.loginHistory.home.createOrEditLabel"
          data-cy="LoginHistoryCreateUpdateHeading"
          v-text="$t('testApp.loginHistory.home.createOrEditLabel')"
        >
          Create or edit a LoginHistory
        </h2>
        <div>
          <div class="form-group" v-if="loginHistory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="loginHistory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.loginHistory.createdAt')" for="login-history-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="login-history-createdAt"
                  v-model="$v.loginHistory.createdAt.$model"
                  name="createdAt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="login-history-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.loginHistory.createdAt.$invalid, invalid: $v.loginHistory.createdAt.$invalid }"
                v-model="$v.loginHistory.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.loginHistory.createdAt.$anyDirty && $v.loginHistory.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.loginHistory.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.loginHistory.userAgent')" for="login-history-userAgent">User Agent</label>
            <input
              type="text"
              class="form-control"
              name="userAgent"
              id="login-history-userAgent"
              data-cy="userAgent"
              :class="{ valid: !$v.loginHistory.userAgent.$invalid, invalid: $v.loginHistory.userAgent.$invalid }"
              v-model="$v.loginHistory.userAgent.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.loginHistory.user')" for="login-history-user">User</label>
            <select class="form-control" id="login-history-user" data-cy="user" name="user" v-model="loginHistory.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="loginHistory.user && userOption.id === loginHistory.user.id ? loginHistory.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.loginHistory.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./login-history-update.component.ts"></script>
