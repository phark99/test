package com.skt.test.service.dto;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class SecretDTO {

    private String secretName ;

    private String secretValue;

    public String getSecretName() {
		return secretName;
	}

	public void setSecretName(String secretName) {
		this.secretName = secretName;
	}

	public String getSecretValue() {
		return secretValue;
	}

	public void setSecretValue(String secretValue) {
		this.secretValue = secretValue;
	}

	public SecretDTO() {
        // Empty constructor needed for Jackson.
    }

	@Override
	public String toString() {
		return "SecretDTO [secretName=" + secretName + ", secretValue=" + secretValue + "]";
	}
    
}
