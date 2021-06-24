<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.apiMeta.home.createOrEditLabel"
          data-cy="ApiMetaCreateUpdateHeading"
          v-text="$t('testApp.apiMeta.home.createOrEditLabel')"
        >
          Create or edit a ApiMeta
        </h2>
        <div>
          <div class="form-group" v-if="apiMeta.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="apiMeta.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.appId')" for="api-meta-appId">App Id</label>
            <input
              type="number"
              class="form-control"
              name="appId"
              id="api-meta-appId"
              data-cy="appId"
              :class="{ valid: !$v.apiMeta.appId.$invalid, invalid: $v.apiMeta.appId.$invalid }"
              v-model.number="$v.apiMeta.appId.$model"
              required
            />
            <div v-if="$v.apiMeta.appId.$anyDirty && $v.apiMeta.appId.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiMeta.appId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.apiMeta.appId.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.type')" for="api-meta-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="api-meta-type"
              data-cy="type"
              :class="{ valid: !$v.apiMeta.type.$invalid, invalid: $v.apiMeta.type.$invalid }"
              v-model="$v.apiMeta.type.$model"
              required
            />
            <div v-if="$v.apiMeta.type.$anyDirty && $v.apiMeta.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiMeta.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.value')" for="api-meta-value">Value</label>
            <textarea
              class="form-control"
              name="value"
              id="api-meta-value"
              data-cy="value"
              :class="{ valid: !$v.apiMeta.value.$invalid, invalid: $v.apiMeta.value.$invalid }"
              v-model="$v.apiMeta.value.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.createdBy')" for="api-meta-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="api-meta-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.apiMeta.createdBy.$invalid, invalid: $v.apiMeta.createdBy.$invalid }"
              v-model.number="$v.apiMeta.createdBy.$model"
              required
            />
            <div v-if="$v.apiMeta.createdBy.$anyDirty && $v.apiMeta.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiMeta.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.apiMeta.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.createdAt')" for="api-meta-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="api-meta-createdAt"
                  v-model="$v.apiMeta.createdAt.$model"
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
                id="api-meta-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.apiMeta.createdAt.$invalid, invalid: $v.apiMeta.createdAt.$invalid }"
                v-model="$v.apiMeta.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.apiMeta.createdAt.$anyDirty && $v.apiMeta.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiMeta.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.modifiedBy')" for="api-meta-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="api-meta-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.apiMeta.modifiedBy.$invalid, invalid: $v.apiMeta.modifiedBy.$invalid }"
              v-model.number="$v.apiMeta.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.modifiedAt')" for="api-meta-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="api-meta-modifiedAt"
                  v-model="$v.apiMeta.modifiedAt.$model"
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
                id="api-meta-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.apiMeta.modifiedAt.$invalid, invalid: $v.apiMeta.modifiedAt.$invalid }"
                v-model="$v.apiMeta.modifiedAt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiMeta.api')" for="api-meta-api">Api</label>
            <select class="form-control" id="api-meta-api" data-cy="api" name="api" v-model="apiMeta.api">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="apiMeta.api && apiOption.id === apiMeta.api.id ? apiMeta.api : apiOption"
                v-for="apiOption in apis"
                :key="apiOption.id"
              >
                {{ apiOption.id }}
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
            :disabled="$v.apiMeta.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./api-meta-update.component.ts"></script>
