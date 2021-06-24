<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.resourceMeta.home.createOrEditLabel"
          data-cy="ResourceMetaCreateUpdateHeading"
          v-text="$t('testApp.resourceMeta.home.createOrEditLabel')"
        >
          Create or edit a ResourceMeta
        </h2>
        <div>
          <div class="form-group" v-if="resourceMeta.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="resourceMeta.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resourceMeta.type')" for="resource-meta-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="resource-meta-type"
              data-cy="type"
              :class="{ valid: !$v.resourceMeta.type.$invalid, invalid: $v.resourceMeta.type.$invalid }"
              v-model="$v.resourceMeta.type.$model"
              required
            />
            <div v-if="$v.resourceMeta.type.$anyDirty && $v.resourceMeta.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.resourceMeta.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resourceMeta.value')" for="resource-meta-value">Value</label>
            <input
              type="text"
              class="form-control"
              name="value"
              id="resource-meta-value"
              data-cy="value"
              :class="{ valid: !$v.resourceMeta.value.$invalid, invalid: $v.resourceMeta.value.$invalid }"
              v-model="$v.resourceMeta.value.$model"
              required
            />
            <div v-if="$v.resourceMeta.value.$anyDirty && $v.resourceMeta.value.$invalid">
              <small class="form-text text-danger" v-if="!$v.resourceMeta.value.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resourceMeta.createdBy')" for="resource-meta-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="resource-meta-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.resourceMeta.createdBy.$invalid, invalid: $v.resourceMeta.createdBy.$invalid }"
              v-model.number="$v.resourceMeta.createdBy.$model"
              required
            />
            <div v-if="$v.resourceMeta.createdBy.$anyDirty && $v.resourceMeta.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.resourceMeta.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.resourceMeta.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resourceMeta.createdAt')" for="resource-meta-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="resource-meta-createdAt"
                  v-model="$v.resourceMeta.createdAt.$model"
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
                id="resource-meta-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.resourceMeta.createdAt.$invalid, invalid: $v.resourceMeta.createdAt.$invalid }"
                v-model="$v.resourceMeta.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.resourceMeta.createdAt.$anyDirty && $v.resourceMeta.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.resourceMeta.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resourceMeta.modifiedBy')" for="resource-meta-modifiedBy"
              >Modified By</label
            >
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="resource-meta-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.resourceMeta.modifiedBy.$invalid, invalid: $v.resourceMeta.modifiedBy.$invalid }"
              v-model.number="$v.resourceMeta.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resourceMeta.modifiedAt')" for="resource-meta-modifiedAt"
              >Modified At</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="resource-meta-modifiedAt"
                  v-model="$v.resourceMeta.modifiedAt.$model"
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
                id="resource-meta-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.resourceMeta.modifiedAt.$invalid, invalid: $v.resourceMeta.modifiedAt.$invalid }"
                v-model="$v.resourceMeta.modifiedAt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.resourceMeta.resource')" for="resource-meta-resource">Resource</label>
            <select class="form-control" id="resource-meta-resource" data-cy="resource" name="resource" v-model="resourceMeta.resource">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  resourceMeta.resource && resourceOption.id === resourceMeta.resource.id ? resourceMeta.resource : resourceOption
                "
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
            :disabled="$v.resourceMeta.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./resource-meta-update.component.ts"></script>
