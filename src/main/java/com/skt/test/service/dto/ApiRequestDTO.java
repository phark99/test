package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.ApiRequest} entity.
 */
@ApiModel(description = "The ApiRequest entity.\n@author A true hipster")
public class ApiRequestDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * api_id
     */
    @NotNull
    @ApiModelProperty(value = "api_id", required = true)
    private Long apiId;

    /**
     * client_type
     */
    @NotNull
    @ApiModelProperty(value = "client_type", required = true)
    private String clientType;

    /**
     * project_id
     */
    @NotNull
    @ApiModelProperty(value = "project_id", required = true)
    private Long projectId;

    /**
     * description
     */
    @ApiModelProperty(value = "description")
    private String description;

    /**
     * created_by
     */
    @NotNull
    @ApiModelProperty(value = "created_by", required = true)
    private Long createdBy;

    /**
     * created_at
     */
    @NotNull
    @ApiModelProperty(value = "created_at", required = true)
    private LocalDate createdAt;

    /**
     * modified_by
     */
    @ApiModelProperty(value = "modified_by")
    private Long modifiedBy;

    /**
     * modified_at
     */
    @ApiModelProperty(value = "modified_at")
    private LocalDate modifiedAt;

    private Set<ApiDTO> apis = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<ApiDTO> getApis() {
        return apis;
    }

    public void setApis(Set<ApiDTO> apis) {
        this.apis = apis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiRequestDTO)) {
            return false;
        }

        ApiRequestDTO apiRequestDTO = (ApiRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apiRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiRequestDTO{" +
            "id=" + getId() +
            ", apiId=" + getApiId() +
            ", clientType='" + getClientType() + "'" +
            ", projectId=" + getProjectId() +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", apis=" + getApis() +
            "}";
    }
}
