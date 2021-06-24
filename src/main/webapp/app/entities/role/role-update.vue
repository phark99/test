<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="testApp.role.home.createOrEditLabel" data-cy="RoleCreateUpdateHeading" v-text="$t('testApp.role.home.createOrEditLabel')">
          Create or edit a Role
        </h2>
        <div>
          <div class="form-group" v-if="role.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="role.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.name')" for="role-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="role-name"
              data-cy="name"
              :class="{ valid: !$v.role.name.$invalid, invalid: $v.role.name.$invalid }"
              v-model="$v.role.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.description')" for="role-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="role-description"
              data-cy="description"
              :class="{ valid: !$v.role.description.$invalid, invalid: $v.role.description.$invalid }"
              v-model="$v.role.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.type')" for="role-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="role-type"
              data-cy="type"
              :class="{ valid: !$v.role.type.$invalid, invalid: $v.role.type.$invalid }"
              v-model="$v.role.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.applyTarget')" for="role-applyTarget">Apply Target</label>
            <input
              type="text"
              class="form-control"
              name="applyTarget"
              id="role-applyTarget"
              data-cy="applyTarget"
              :class="{ valid: !$v.role.applyTarget.$invalid, invalid: $v.role.applyTarget.$invalid }"
              v-model="$v.role.applyTarget.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.permission')" for="role-permission">Permission</label>
            <input
              type="text"
              class="form-control"
              name="permission"
              id="role-permission"
              data-cy="permission"
              :class="{ valid: !$v.role.permission.$invalid, invalid: $v.role.permission.$invalid }"
              v-model="$v.role.permission.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.createdBy')" for="role-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="role-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.role.createdBy.$invalid, invalid: $v.role.createdBy.$invalid }"
              v-model.number="$v.role.createdBy.$model"
              required
            />
            <div v-if="$v.role.createdBy.$anyDirty && $v.role.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.role.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.role.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.createdAt')" for="role-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="role-createdAt"
                  v-model="$v.role.createdAt.$model"
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
                id="role-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.role.createdAt.$invalid, invalid: $v.role.createdAt.$invalid }"
                v-model="$v.role.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.role.createdAt.$anyDirty && $v.role.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.role.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.modifiedBy')" for="role-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="role-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.role.modifiedBy.$invalid, invalid: $v.role.modifiedBy.$invalid }"
              v-model.number="$v.role.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.role.modifiedAt')" for="role-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="role-modifiedAt"
                  v-model="$v.role.modifiedAt.$model"
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
                id="role-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.role.modifiedAt.$invalid, invalid: $v.role.modifiedAt.$invalid }"
                v-model="$v.role.modifiedAt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label v-text="$t('testApp.role.user')" for="role-user">User</label>
            <select
              class="form-control"
              id="role-user"
              data-cy="user"
              multiple
              name="user"
              v-if="role.users !== undefined"
              v-model="role.users"
            >
              <option v-bind:value="getSelected(role.users, userOption)" v-for="userOption in users" :key="userOption.id">
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('testApp.role.resource')" for="role-resource">Resource</label>
            <select
              class="form-control"
              id="role-resource"
              data-cy="resource"
              multiple
              name="resource"
              v-if="role.resources !== undefined"
              v-model="role.resources"
            >
              <option
                v-bind:value="getSelected(role.resources, resourceOption)"
                v-for="resourceOption in resources"
                :key="resourceOption.id"
              >
                {{ resourceOption.id }}
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
            :disabled="$v.role.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./role-update.component.ts"></script>
