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
 * A DTO for the {@link com.skt.test.domain.Project} entity.
 */
@ApiModel(description = "The Project entity.\n@author A true hipster")
public class ProjectDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * description
     */
    @ApiModelProperty(value = "description")
    private String description;

    /**
     * deptCode
     */
    @ApiModelProperty(value = "deptCode")
    private String deptCode;

    /**
     * tags
     */
    @ApiModelProperty(value = "tags")
    private String tags;

    /**
     * status
     */
    @ApiModelProperty(value = "status")
    private String status;

    /**
     * mainAdminId
     */
    @ApiModelProperty(value = "mainAdminId")
    private Long mainAdminId;

    /**
     * createdBy
     */
    @NotNull
    @ApiModelProperty(value = "createdBy", required = true)
    private Long createdBy;

    /**
     * createdAt
     */
    @NotNull
    @ApiModelProperty(value = "createdAt", required = true)
    private LocalDate createdAt;

    /**
     * modifiedBy
     */
    @ApiModelProperty(value = "modifiedBy")
    private Long modifiedBy;

    /**
     * modifiedAt
     */
    @ApiModelProperty(value = "modifiedAt")
    private LocalDate modifiedAt;

    private Set<ResourceDTO> resources = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMainAdminId() {
        return mainAdminId;
    }

    public void setMainAdminId(Long mainAdminId) {
        this.mainAdminId = mainAdminId;
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

    public Set<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceDTO> resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", deptCode='" + getDeptCode() + "'" +
            ", tags='" + getTags() + "'" +
            ", status='" + getStatus() + "'" +
            ", mainAdminId=" + getMainAdminId() +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", resources=" + getResources() +
            "}";
    }
}
