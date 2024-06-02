package com.task.mediasoft.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.task.mediasoft.configuration.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3 {

    private final S3Properties s3Properties;

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                                "s3.localhost.localstack.cloud:4566",
                                Regions.EU_CENTRAL_1.getName()
                        )
                )
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3Properties.getAccessKeyId(), s3Properties.getSecretAccessKey())))
                .build();
    }
}
