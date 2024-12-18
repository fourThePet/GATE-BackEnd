package com.ureca.gate.global.config.personalize;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;

@Configuration
public class personalizeConfig {
    @Value("${spring.cloud.aws.credentials.access-key}")
    String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    String secretKey;

    @Value("${spring.cloud.aws.s3.region}")
    String region;

    @Bean
    public PersonalizeRuntimeClient personalizeRuntimeClient() {
        return PersonalizeRuntimeClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }
}
