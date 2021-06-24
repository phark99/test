package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.ProjectHistory} entity.
 */
@ApiModel(description = "The ProjectHistory entity.\n@author A true hipster")
public class ProjectHistoryDTO implements Serializable {

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

    private ProjectDTO project;

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

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectHistoryDTO)) {
            return false;
        }

        ProjectHistoryDTO projectHistoryDTO = (ProjectHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectHistoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", deptCode='" + getDeptCode() + "'" +
            ", tags='" + getTags() + "'" +
            ", status='" + getStatus() + "'" +
            ", mainAdminId=" + getMainAdminId() +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
