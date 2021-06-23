package com.skt.test.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skt.test.service.dto.SecretDTO;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretResponse;
import software.amazon.awssdk.services.secretsmanager.model.DeleteSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;
import software.amazon.awssdk.services.secretsmanager.model.UpdateSecretRequest;

/**
 * SecretManagerResource
 * @author P164451
 *
 */
@RestController
@RequestMapping("/api/secret-manager")
public class SecretManagerResource {
	
	private final Logger log = LoggerFactory.getLogger(SecretManagerResource.class);

	public SecretManagerResource() {
		
	}

	/**
	 * getSecret
	 * 
	 * @param secretName
	 * @return
	 */
	@GetMapping("/secret")
	public String getSecret(@RequestParam(defaultValue = "testSecret") String secretName) {
		log.debug("REST request to getSecret");

		String secret = "";
		Region region = Region.AP_NORTHEAST_2;
		SecretsManagerClient secretsClient = SecretsManagerClient.builder().region(region).build();
		try {
			GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretName).build();

			GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
			secret = valueResponse.secretString();
			log.debug(secret);

		} catch (SecretsManagerException e) {
			log.error(e.awsErrorDetails().errorMessage());
		}

		secretsClient.close();    

		return secret;
	}

	/**
	 * createSecret
	 * 
	 * @param secretDTO
	 * @return
	 */
	@PostMapping("/secret")
	public String createSecret(@RequestBody SecretDTO secretDTO) {
		String secretARN = "";
		Region region = Region.AP_NORTHEAST_2;
		SecretsManagerClient secretsClient = SecretsManagerClient.builder().region(region).build();
		try {
			CreateSecretRequest secretRequest = CreateSecretRequest.builder().name(secretDTO.getSecretName())
					.description("This secret was created by the AWS Secret Manager Java API")
					.secretString(secretDTO.getSecretValue()).build();

			CreateSecretResponse secretResponse = secretsClient.createSecret(secretRequest);
			secretARN = secretResponse.arn();

		} catch (SecretsManagerException e) {
			log.error(e.awsErrorDetails().errorMessage());
		}

		secretsClient.close();

		return secretARN;

	}

	/**
	 * updateSecret
	 * @param secretDTO
	 * @return
	 */
	@PutMapping("/secret")
	public String updateSecret(@RequestBody SecretDTO secretDTO) {
		String secret = "";
		Region region = Region.AP_NORTHEAST_2;
		SecretsManagerClient secretsClient = SecretsManagerClient.builder().region(region).build();
		try {
			UpdateSecretRequest secretRequest = UpdateSecretRequest.builder().secretId(secretDTO.getSecretName())
					.secretString(secretDTO.getSecretValue()).build();

			secretsClient.updateSecret(secretRequest);

		} catch (SecretsManagerException e) {
			log.error(e.awsErrorDetails().errorMessage());
		}

		secretsClient.close();

		return secret;
	}
	
	/**
	 * deleteSecret
	 * @param secretName
	 * @return
	 */
	@DeleteMapping("/secret")
	public String deleteSecret(@RequestParam(defaultValue = "testSecret") String secretName) {
		String secret = "";
		Region region = Region.AP_NORTHEAST_2;
		SecretsManagerClient secretsClient = SecretsManagerClient.builder().region(region).build();
		try {
			DeleteSecretRequest secretRequest = DeleteSecretRequest.builder()
	                .secretId(secretName)
	                .build();

	            secretsClient.deleteSecret(secretRequest);
	            log.debug(secretName +" is deleted.");

		} catch (SecretsManagerException e) {
			log.error(e.awsErrorDetails().errorMessage());
		}

		secretsClient.close();

		return secret;
	}

}
