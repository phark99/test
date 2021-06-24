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
 * The ApiRequest entity.\n@author A true hipster
 */
@Entity
@Table(name = "api_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApiRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * api_id
     */
    @NotNull
    @Column(name = "api_id", nullable = false)
    private Long apiId;

    /**
     * client_type
     */
    @NotNull
    @Column(name = "client_type", nullable = false)
    private String clientType;

    /**
     * project_id
     */
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * description
     */
    @Column(name = "description")
    private String description;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_api_request__api",
        joinColumns = @JoinColumn(name = "api_request_id"),
        inverseJoinColumns = @JoinColumn(name = "api_id")
    )
    @JsonIgnoreProperties(value = { "apiMetas", "apiRequests" }, allowSetters = true)
    private Set<Api> apis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApiRequest id(Long id) {
        this.id = id;
        return this;
    }

    public Long getApiId() {
        return this.apiId;
    }

    public ApiRequest apiId(Long apiId) {
        this.apiId = apiId;
        return this;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public String getClientType() {
        return this.clientType;
    }

    public ApiRequest clientType(String clientType) {
        this.clientType = clientType;
        return this;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public ApiRequest projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return this.description;
    }

    public ApiRequest description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public ApiRequest createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public ApiRequest createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public ApiRequest modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return this.modifiedAt;
    }

    public ApiRequest modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<Api> getApis() {
        return this.apis;
    }

    public ApiRequest apis(Set<Api> apis) {
        this.setApis(apis);
        return this;
    }

    public ApiRequest addApi(Api api) {
        this.apis.add(api);
        api.getApiRequests().add(this);
        return this;
    }

    public ApiRequest removeApi(Api api) {
        this.apis.remove(api);
        api.getApiRequests().remove(this);
        return this;
    }

    public void setApis(Set<Api> apis) {
        this.apis = apis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiRequest)) {
            return false;
        }
        return id != null && id.equals(((ApiRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiRequest{" +
            "id=" + getId() +
            ", apiId=" + getApiId() +
            ", clientType='" + getClientType() + "'" +
            ", projectId=" + getProjectId() +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
