<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.userGroup.home.createOrEditLabel"
          data-cy="UserGroupCreateUpdateHeading"
          v-text="$t('testApp.userGroup.home.createOrEditLabel')"
        >
          Create or edit a UserGroup
        </h2>
        <div>
          <div class="form-group" v-if="userGroup.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="userGroup.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userGroup.name')" for="user-group-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="user-group-name"
              data-cy="name"
              :class="{ valid: !$v.userGroup.name.$invalid, invalid: $v.userGroup.name.$invalid }"
              v-model="$v.userGroup.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userGroup.status')" for="user-group-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="user-group-status"
              data-cy="status"
              :class="{ valid: !$v.userGroup.status.$invalid, invalid: $v.userGroup.status.$invalid }"
              v-model="$v.userGroup.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userGroup.createdBy')" for="user-group-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="user-group-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.userGroup.createdBy.$invalid, invalid: $v.userGroup.createdBy.$invalid }"
              v-model.number="$v.userGroup.createdBy.$model"
              required
            />
            <div v-if="$v.userGroup.createdBy.$anyDirty && $v.userGroup.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.userGroup.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.userGroup.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userGroup.createdAt')" for="user-group-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="user-group-createdAt"
                  v-model="$v.userGroup.createdAt.$model"
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
                id="user-group-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.userGroup.createdAt.$invalid, invalid: $v.userGroup.createdAt.$invalid }"
                v-model="$v.userGroup.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.userGroup.createdAt.$anyDirty && $v.userGroup.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.userGroup.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userGroup.modifiedBy')" for="user-group-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="user-group-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.userGroup.modifiedBy.$invalid, invalid: $v.userGroup.modifiedBy.$invalid }"
              v-model.number="$v.userGroup.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.userGroup.modifiedAt')" for="user-group-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="user-group-modifiedAt"
                  v-model="$v.userGroup.modifiedAt.$model"
                  name="modifiedAt"
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
                id="user-group-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.userGroup.modifiedAt.$invalid, invalid: $v.userGroup.modifiedAt.$invalid }"
                v-model="$v.userGroup.modifiedAt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label v-text="$t('testApp.userGroup.user')" for="user-group-user">User</label>
            <select
              class="form-control"
              id="user-group-user"
              data-cy="user"
              multiple
              name="user"
              v-if="userGroup.users !== undefined"
              v-model="userGroup.users"
            >
              <option v-bind:value="getSelected(userGroup.users, userOption)" v-for="userOption in users" :key="userOption.id">
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('testApp.userGroup.role')" for="user-group-role">Role</label>
            <select
              class="form-control"
              id="user-group-role"
              data-cy="role"
              multiple
              name="role"
              v-if="userGroup.roles !== undefined"
              v-model="userGroup.roles"
            >
              <option v-bind:value="getSelected(userGroup.roles, roleOption)" v-for="roleOption in roles" :key="roleOption.id">
                {{ roleOption.id }}
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
            :disabled="$v.userGroup.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./user-group-update.component.ts"></script>
