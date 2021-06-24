package com.skt.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Project entity.\n@author A true hipster
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Serializable {

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

    /**
     * modifiedBy
     */
    @Column(name = "modified_by")
    private Long modifiedBy;

    /**
     * modifiedAt
     */
    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_project__resource",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    @JsonIgnoreProperties(value = { "resourceMetas", "roles", "projectResources" }, allowSetters = true)
    private Set<Resource> resources = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    private Set<ProjectHistory> projectHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public Project deptCode(String deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getTags() {
        return this.tags;
    }

    public Project tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return this.status;
    }

    public Project status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMainAdminId() {
        return this.mainAdminId;
    }

    public Project mainAdminId(Long mainAdminId) {
        this.mainAdminId = mainAdminId;
        return this;
    }

    public void setMainAdminId(Long mainAdminId) {
        this.mainAdminId = mainAdminId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Project createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Project createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Project modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return this.modifiedAt;
    }

    public Project modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<Resource> getResources() {
        return this.resources;
    }

    public Project resources(Set<Resource> resources) {
        this.setResources(resources);
        return this;
    }

    public Project addResource(Resource resource) {
        this.resources.add(resource);
        resource.getProjectResources().add(this);
        return this;
    }

    public Project removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.getProjectResources().remove(this);
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public Set<ProjectHistory> getProjectHistories() {
        return this.projectHistories;
    }

    public Project projectHistories(Set<ProjectHistory> projectHistories) {
        this.setProjectHistories(projectHistories);
        return this;
    }

    public Project addProjectHistory(ProjectHistory projectHistory) {
        this.projectHistories.add(projectHistory);
        projectHistory.setProject(this);
        return this;
    }

    public Project removeProjectHistory(ProjectHistory projectHistory) {
        this.projectHistories.remove(projectHistory);
        projectHistory.setProject(null);
        return this;
    }

    public void setProjectHistories(Set<ProjectHistory> projectHistories) {
        if (this.projectHistories != null) {
            this.projectHistories.forEach(i -> i.setProject(null));
        }
        if (projectHistories != null) {
            projectHistories.forEach(i -> i.setProject(this));
        }
        this.projectHistories = projectHistories;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
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
            "}";
    }
}
