package org.shared.model;

import lombok.NoArgsConstructor;
import org.shared.model.utils.SchemaUtil;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.UUID;

@DynamoDbBean
@NoArgsConstructor
//@RegisterForReflection
public class MyTableItemDynamicSchema {

    private String PK;
    private String SK;
    private String uniqueId;

    public MyTableItemDynamicSchema(UUID uniqueId) {
        this.PK = "MYTABLEITEM#" + uniqueId;
        this.SK = "MYTABLEITEM#" + uniqueId;
        this.uniqueId = uniqueId.toString();
    }

    @DynamoDbPartitionKey
    public String getPK() {
        return this.PK;
    }

    @DynamoDbSortKey
    public String getSK() {
        return this.SK;
    }

    @DynamoDbAttribute("UniqueId")
    public String getUniqueId() {
        return this.uniqueId;
    }

    public static TableSchema<MyTableItemDynamicSchema> DYNAMIC_SCHEMA =
            SchemaUtil.generateSchema(MyTableItemDynamicSchema.class);

}