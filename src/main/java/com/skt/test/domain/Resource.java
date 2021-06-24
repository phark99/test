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
 * The Resource entity.\n@author A true hipster
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * type
     */
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * name
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * status
     */
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * created_by
     */
    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    /**
     * created_at
     */
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    /**
     * modified_by
     */
    @Column(name = "modified_by")
    private Long modifiedBy;

    /**
     * modified_at
     */
    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource" }, allowSetters = true)
    private Set<ResourceMeta> resourceMetas = new HashSet<>();

    @ManyToMany(mappedBy = "resources")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "resources", "menus", "userGroups" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "resources")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resources", "projectHistories" }, allowSetters = true)
    private Set<Project> projectResources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resource id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Resource type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Resource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public Resource status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Resource createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Resource createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Resource modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return this.modifiedAt;
    }

    public Resource modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<ResourceMeta> getResourceMetas() {
        return this.resourceMetas;
    }

    public Resource resourceMetas(Set<ResourceMeta> resourceMetas) {
        this.setResourceMetas(resourceMetas);
        return this;
    }

    public Resource addResourceMeta(ResourceMeta resourceMeta) {
        this.resourceMetas.add(resourceMeta);
        resourceMeta.setResource(this);
        return this;
    }

    public Resource removeResourceMeta(ResourceMeta resourceMeta) {
        this.resourceMetas.remove(resourceMeta);
        resourceMeta.setResource(null);
        return this;
    }

    public void setResourceMetas(Set<ResourceMeta> resourceMetas) {
        if (this.resourceMetas != null) {
            this.resourceMetas.forEach(i -> i.setResource(null));
        }
        if (resourceMetas != null) {
            resourceMetas.forEach(i -> i.setResource(this));
        }
        this.resourceMetas = resourceMetas;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public Resource roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Resource addRole(Role role) {
        this.roles.add(role);
        role.getResources().add(this);
        return this;
    }

    public Resource removeRole(Role role) {
        this.roles.remove(role);
        role.getResources().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.removeResource(this));
        }
        if (roles != null) {
            roles.forEach(i -> i.addResource(this));
        }
        this.roles = roles;
    }

    public Set<Project> getProjectResources() {
        return this.projectResources;
    }

    public Resource projectResources(Set<Project> projects) {
        this.setProjectResources(projects);
        return this;
    }

    public Resource addProjectResource(Project project) {
        this.projectResources.add(project);
        project.getResources().add(this);
        return this;
    }

    public Resource removeProjectResource(Project project) {
        this.projectResources.remove(project);
        project.getResources().remove(this);
        return this;
    }

    public void setProjectResources(Set<Project> projects) {
        if (this.projectResources != null) {
            this.projectResources.forEach(i -> i.removeResource(this));
        }
        if (projects != null) {
            projects.forEach(i -> i.addResource(this));
        }
        this.projectResources = projects;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
