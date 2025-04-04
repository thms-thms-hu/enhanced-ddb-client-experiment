package org.write_to_db;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.shared.DynamoDBService;
import org.shared.model.MyTableItemDynamicSchema;
import org.shared.model.MyTableItemStaticSchema;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.UUID;

@Named("writeToDb")
@ApplicationScoped
public class WriteToDb implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    DynamoDBService dynamoDBService;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        DynamoDbEnhancedClient dbClient = this.dynamoDBService.dbEnhancedClient();

        // DYNAMICALLY CREATED SCHEMA
        DynamoDbTable<MyTableItemDynamicSchema> tableDynamic = dbClient.table("MyTable", MyTableItemDynamicSchema.DYNAMIC_SCHEMA);

        MyTableItemDynamicSchema myTableItemDynamicSchema = new MyTableItemDynamicSchema(UUID.randomUUID());
        tableDynamic.putItem(myTableItemDynamicSchema);

        // STATIC SCHEMA
        DynamoDbTable<MyTableItemStaticSchema> tableStatic = dbClient.table("MyTable", MyTableItemStaticSchema.STATIC_SCHEMA);

        MyTableItemStaticSchema myTableItemStaticSchema = new MyTableItemStaticSchema(UUID.randomUUID());
        tableStatic.putItem(myTableItemStaticSchema);

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(null);

    }
}
