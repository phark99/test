<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.projectHistory.home.createOrEditLabel"
          data-cy="ProjectHistoryCreateUpdateHeading"
          v-text="$t('testApp.projectHistory.home.createOrEditLabel')"
        >
          Create or edit a ProjectHistory
        </h2>
        <div>
          <div class="form-group" v-if="projectHistory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="projectHistory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.name')" for="project-history-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="project-history-name"
              data-cy="name"
              :class="{ valid: !$v.projectHistory.name.$invalid, invalid: $v.projectHistory.name.$invalid }"
              v-model="$v.projectHistory.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.description')" for="project-history-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="project-history-description"
              data-cy="description"
              :class="{ valid: !$v.projectHistory.description.$invalid, invalid: $v.projectHistory.description.$invalid }"
              v-model="$v.projectHistory.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.deptCode')" for="project-history-deptCode"
              >Dept Code</label
            >
            <input
              type="text"
              class="form-control"
              name="deptCode"
              id="project-history-deptCode"
              data-cy="deptCode"
              :class="{ valid: !$v.projectHistory.deptCode.$invalid, invalid: $v.projectHistory.deptCode.$invalid }"
              v-model="$v.projectHistory.deptCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.tags')" for="project-history-tags">Tags</label>
            <input
              type="text"
              class="form-control"
              name="tags"
              id="project-history-tags"
              data-cy="tags"
              :class="{ valid: !$v.projectHistory.tags.$invalid, invalid: $v.projectHistory.tags.$invalid }"
              v-model="$v.projectHistory.tags.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.status')" for="project-history-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="project-history-status"
              data-cy="status"
              :class="{ valid: !$v.projectHistory.status.$invalid, invalid: $v.projectHistory.status.$invalid }"
              v-model="$v.projectHistory.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.mainAdminId')" for="project-history-mainAdminId"
              >Main Admin Id</label
            >
            <input
              type="number"
              class="form-control"
              name="mainAdminId"
              id="project-history-mainAdminId"
              data-cy="mainAdminId"
              :class="{ valid: !$v.projectHistory.mainAdminId.$invalid, invalid: $v.projectHistory.mainAdminId.$invalid }"
              v-model.number="$v.projectHistory.mainAdminId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.createdBy')" for="project-history-createdBy"
              >Created By</label
            >
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="project-history-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.projectHistory.createdBy.$invalid, invalid: $v.projectHistory.createdBy.$invalid }"
              v-model.number="$v.projectHistory.createdBy.$model"
              required
            />
            <div v-if="$v.projectHistory.createdBy.$anyDirty && $v.projectHistory.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.projectHistory.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.projectHistory.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.createdAt')" for="project-history-createdAt"
              >Created At</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="project-history-createdAt"
                  v-model="$v.projectHistory.createdAt.$model"
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
                id="project-history-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.projectHistory.createdAt.$invalid, invalid: $v.projectHistory.createdAt.$invalid }"
                v-model="$v.projectHistory.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.projectHistory.createdAt.$anyDirty && $v.projectHistory.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.projectHistory.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.projectHistory.project')" for="project-history-project">Project</label>
            <select class="form-control" id="project-history-project" data-cy="project" name="project" v-model="projectHistory.project">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  projectHistory.project && projectOption.id === projectHistory.project.id ? projectHistory.project : projectOption
                "
                v-for="projectOption in projects"
                :key="projectOption.id"
              >
                {{ projectOption.id }}
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
            :disabled="$v.projectHistory.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./project-history-update.component.ts"></script>
