package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.Api} entity.
 */
@ApiModel(description = "The api entity.\n@author A true hipster")
public class ApiDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * name
     */
    @NotNull
    @ApiModelProperty(value = "name", required = true)
    private String name;

    /**
     * project_id
     */
    @NotNull
    @ApiModelProperty(value = "project_id", required = true)
    private Long projectId;

    /**
     * manager_id
     */
    @NotNull
    @ApiModelProperty(value = "manager_id", required = true)
    private Long managerId;

    /**
     * api_type
     */
    @NotNull
    @ApiModelProperty(value = "api_type", required = true)
    private String apiType;

    /**
     * host
     */
    @ApiModelProperty(value = "host")
    private String host;

    /**
     * port
     */
    @ApiModelProperty(value = "port")
    private Integer port;

    /**
     * uri
     */
    @ApiModelProperty(value = "uri")
    private String uri;

    /**
     * version
     */
    @ApiModelProperty(value = "version")
    private String version;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiDTO)) {
            return false;
        }

        ApiDTO apiDTO = (ApiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", projectId=" + getProjectId() +
            ", managerId=" + getManagerId() +
            ", apiType='" + getApiType() + "'" +
            ", host='" + getHost() + "'" +
            ", port=" + getPort() +
            ", uri='" + getUri() + "'" +
            ", version='" + getVersion() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
