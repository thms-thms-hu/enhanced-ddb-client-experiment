package org.shared;

import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class DynamoDBService {

    private final DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .httpClient(UrlConnectionHttpClient.builder().build())
            .build();

    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDBClient)
            .build();

    public DynamoDbEnhancedClient dbEnhancedClient() {
        return this.enhancedClient;
    }

}
