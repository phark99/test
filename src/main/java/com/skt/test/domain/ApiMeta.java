package com.skt.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The ApiMeta entity.\n@author A true hipster
 */
@Entity
@Table(name = "api_meta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApiMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * app_id
     */
    @NotNull
    @Column(name = "app_id", nullable = false)
    private Long appId;

    /**
     * type
     */
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * value
     */
    @Lob
    @Column(name = "value")
    private String value;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "apiMetas", "apiRequests" }, allowSetters = true)
    private Api api;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApiMeta id(Long id) {
        this.id = id;
        return this;
    }

    public Long getAppId() {
        return this.appId;
    }

    public ApiMeta appId(Long appId) {
        this.appId = appId;
        return this;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getType() {
        return this.type;
    }

    public ApiMeta type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public ApiMeta value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public ApiMeta createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public ApiMeta createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public ApiMeta modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return this.modifiedAt;
    }

    public ApiMeta modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Api getApi() {
        return this.api;
    }

    public ApiMeta api(Api api) {
        this.setApi(api);
        return this;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiMeta)) {
            return false;
        }
        return id != null && id.equals(((ApiMeta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiMeta{" +
            "id=" + getId() +
            ", appId=" + getAppId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
