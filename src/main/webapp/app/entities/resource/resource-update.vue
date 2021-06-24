<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.resource.home.createOrEditLabel"
          data-cy="ResourceCreateUpdateHeading"
          v-text="$t('testApp.resource.home.createOrEditLabel')"
        >
          Create or edit a Resource
        </h2>
        <div>
          <div class="form-group" v-if="resource.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="resource.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resource.type')" for="resource-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="resource-type"
              data-cy="type"
              :class="{ valid: !$v.resource.type.$invalid, invalid: $v.resource.type.$invalid }"
              v-model="$v.resource.type.$model"
              required
            />
            <div v-if="$v.resource.type.$anyDirty && $v.resource.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.resource.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resource.name')" for="resource-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="resource-name"
              data-cy="name"
              :class="{ valid: !$v.resource.name.$invalid, invalid: $v.resource.name.$invalid }"
              v-model="$v.resource.name.$model"
              required
            />
            <div v-if="$v.resource.name.$anyDirty && $v.resource.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.resource.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resource.status')" for="resource-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="resource-status"
              data-cy="status"
              :class="{ valid: !$v.resource.status.$invalid, invalid: $v.resource.status.$invalid }"
              v-model="$v.resource.status.$model"
              required
            />
            <div v-if="$v.resource.status.$anyDirty && $v.resource.status.$invalid">
              <small class="form-text text-danger" v-if="!$v.resource.status.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resource.createdBy')" for="resource-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="resource-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.resource.createdBy.$invalid, invalid: $v.resource.createdBy.$invalid }"
              v-model.number="$v.resource.createdBy.$model"
              required
            />
            <div v-if="$v.resource.createdBy.$anyDirty && $v.resource.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.resource.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.resource.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resource.createdAt')" for="resource-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="resource-createdAt"
                  v-model="$v.resource.createdAt.$model"
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
                id="resource-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.resource.createdAt.$invalid, invalid: $v.resource.createdAt.$invalid }"
                v-model="$v.resource.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.resource.createdAt.$anyDirty && $v.resource.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.resource.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resource.modifiedBy')" for="resource-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="resource-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.resource.modifiedBy.$invalid, invalid: $v.resource.modifiedBy.$invalid }"
              v-model.number="$v.resource.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resource.modifiedAt')" for="resource-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="resource-modifiedAt"
                  v-model="$v.resource.modifiedAt.$model"
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
                id="resource-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.resource.modifiedAt.$invalid, invalid: $v.resource.modifiedAt.$invalid }"
                v-model="$v.resource.modifiedAt.$model"
              />
            </b-input-group>
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
            :disabled="$v.resource.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./resource-update.component.ts"></script>
