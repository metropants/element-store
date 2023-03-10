package xyz.metropants.element.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Bean
    public S3AsyncClient client() {
        final AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3AsyncClient.builder()
                .forcePathStyle(false)
                .endpointOverride(URI.create("https://fra1.digitaloceanspaces.com"))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.EU_CENTRAL_1)
                .build();
    }

}
