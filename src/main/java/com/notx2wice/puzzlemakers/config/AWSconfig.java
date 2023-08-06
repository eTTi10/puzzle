package com.notx2wice.puzzlemakers.config;


import com.amazonaws.auth.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.notx2wice.puzzlemakers.repository.dynamo")
public class AWSconfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        InstanceProfileCredentialsProvider provider = new InstanceProfileCredentialsProvider(true);
        return AmazonDynamoDBClientBuilder.standard().withCredentials(provider)
                .withRegion(awsRegion).build();
    }
}
