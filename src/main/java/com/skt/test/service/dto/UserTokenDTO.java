package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.UserToken} entity.
 */
@ApiModel(description = "The UserToken entity.\n@author A true hipster")
public class UserTokenDTO implements Serializable {

    private Long id;

    /**
     * acc_token
     */
    @ApiModelProperty(value = "acc_token")
    private String accToken;

    /**
     * acc_exp_time
     */
    @ApiModelProperty(value = "acc_exp_time")
    private LocalDate accExpTime;

    /**
     * ref_token
     */
    @ApiModelProperty(value = "ref_token")
    private String refToken;

    /**
     * ref_exp_time
     */
    @ApiModelProperty(value = "ref_exp_time")
    private LocalDate refExpTime;

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

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccToken() {
        return accToken;
    }

    public void setAccToken(String accToken) {
        this.accToken = accToken;
    }

    public LocalDate getAccExpTime() {
        return accExpTime;
    }

    public void setAccExpTime(LocalDate accExpTime) {
        this.accExpTime = accExpTime;
    }

    public String getRefToken() {
        return refToken;
    }

    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }

    public LocalDate getRefExpTime() {
        return refExpTime;
    }

    public void setRefExpTime(LocalDate refExpTime) {
        this.refExpTime = refExpTime;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTokenDTO)) {
            return false;
        }

        UserTokenDTO userTokenDTO = (UserTokenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userTokenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTokenDTO{" +
            "id=" + getId() +
            ", accToken='" + getAccToken() + "'" +
            ", accExpTime='" + getAccExpTime() + "'" +
            ", refToken='" + getRefToken() + "'" +
            ", refExpTime='" + getRefExpTime() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
