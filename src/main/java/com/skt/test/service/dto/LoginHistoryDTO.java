package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.LoginHistory} entity.
 */
@ApiModel(description = "The LoginHistory entity.\n@author A true hipster")
public class LoginHistoryDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * created_at
     */
    @NotNull
    @ApiModelProperty(value = "created_at", required = true)
    private LocalDate createdAt;

    /**
     * userAgent
     */
    @ApiModelProperty(value = "userAgent")
    private String userAgent;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
        if (!(o instanceof LoginHistoryDTO)) {
            return false;
        }

        LoginHistoryDTO loginHistoryDTO = (LoginHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loginHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoginHistoryDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
