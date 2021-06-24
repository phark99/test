<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.userToken.home.createOrEditLabel"
          data-cy="UserTokenCreateUpdateHeading"
          v-text="$t('testApp.userToken.home.createOrEditLabel')"
        >
          Create or edit a UserToken
        </h2>
        <div>
          <div class="form-group" v-if="userToken.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="userToken.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userToken.accToken')" for="user-token-accToken">Acc Token</label>
            <input
              type="text"
              class="form-control"
              name="accToken"
              id="user-token-accToken"
              data-cy="accToken"
              :class="{ valid: !$v.userToken.accToken.$invalid, invalid: $v.userToken.accToken.$invalid }"
              v-model="$v.userToken.accToken.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userToken.accExpTime')" for="user-token-accExpTime">Acc Exp Time</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="user-token-accExpTime"
                  v-model="$v.userToken.accExpTime.$model"
                  name="accExpTime"
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
                id="user-token-accExpTime"
                data-cy="accExpTime"
                type="text"
                class="form-control"
                name="accExpTime"
                :class="{ valid: !$v.userToken.accExpTime.$invalid, invalid: $v.userToken.accExpTime.$invalid }"
                v-model="$v.userToken.accExpTime.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userToken.refToken')" for="user-token-refToken">Ref Token</label>
            <input
              type="text"
              class="form-control"
              name="refToken"
              id="user-token-refToken"
              data-cy="refToken"
              :class="{ valid: !$v.userToken.refToken.$invalid, invalid: $v.userToken.refToken.$invalid }"
              v-model="$v.userToken.refToken.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userToken.refExpTime')" for="user-token-refExpTime">Ref Exp Time</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="user-token-refExpTime"
                  v-model="$v.userToken.refExpTime.$model"
                  name="refExpTime"
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
                id="user-token-refExpTime"
                data-cy="refExpTime"
                type="text"
                class="form-control"
                name="refExpTime"
                :class="{ valid: !$v.userToken.refExpTime.$invalid, invalid: $v.userToken.refExpTime.$invalid }"
                v-model="$v.userToken.refExpTime.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userToken.createdBy')" for="user-token-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="user-token-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.userToken.createdBy.$invalid, invalid: $v.userToken.createdBy.$invalid }"
              v-model.number="$v.userToken.createdBy.$model"
              required
            />
            <div v-if="$v.userToken.createdBy.$anyDirty && $v.userToken.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.userToken.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.userToken.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userToken.createdAt')" for="user-token-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="user-token-createdAt"
                  v-model="$v.userToken.createdAt.$model"
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
                id="user-token-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.userToken.createdAt.$invalid, invalid: $v.userToken.createdAt.$invalid }"
                v-model="$v.userToken.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.userToken.createdAt.$anyDirty && $v.userToken.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.userToken.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userToken.user')" for="user-token-user">User</label>
            <select class="form-control" id="user-token-user" data-cy="user" name="user" v-model="userToken.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="userToken.user && userOption.id === userToken.user.id ? userToken.user : userOption"
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
            :disabled="$v.userToken.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./user-token-update.component.ts"></script>
