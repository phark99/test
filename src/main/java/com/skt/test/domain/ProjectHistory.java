package com.skt.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The ProjectHistory entity.\n@author A true hipster
 */
@Entity
@Table(name = "project_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name
     */
    @Column(name = "name")
    private String name;

    /**
     * description
     */
    @Column(name = "description")
    private String description;

    /**
     * deptCode
     */
    @Column(name = "dept_code")
    private String deptCode;

    /**
     * tags
     */
    @Column(name = "tags")
    private String tags;

    /**
     * status
     */
    @Column(name = "status")
    private String status;

    /**
     * mainAdminId
     */
    @Column(name = "main_admin_id")
    private Long mainAdminId;

    /**
     * createdBy
     */
    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    /**
     * createdAt
     */
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "resources", "projectHistories" }, allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectHistory id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProjectHistory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ProjectHistory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public ProjectHistory deptCode(String deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getTags() {
        return this.tags;
    }

    public ProjectHistory tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return this.status;
    }

    public ProjectHistory status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMainAdminId() {
        return this.mainAdminId;
    }

    public ProjectHistory mainAdminId(Long mainAdminId) {
        this.mainAdminId = mainAdminId;
        return this;
    }

    public void setMainAdminId(Long mainAdminId) {
        this.mainAdminId = mainAdminId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public ProjectHistory createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public ProjectHistory createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Project getProject() {
        return this.project;
    }

    public ProjectHistory project(Project project) {
        this.setProject(project);
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectHistory)) {
            return false;
        }
        return id != null && id.equals(((ProjectHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectHistory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", deptCode='" + getDeptCode() + "'" +
            ", tags='" + getTags() + "'" +
            ", status='" + getStatus() + "'" +
            ", mainAdminId=" + getMainAdminId() +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
