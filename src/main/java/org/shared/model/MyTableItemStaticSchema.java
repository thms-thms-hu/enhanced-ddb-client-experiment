package org.shared.model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.UUID;

@DynamoDbBean
@NoArgsConstructor
@Setter
public class MyTableItemStaticSchema {

    private String PK;
    private String SK;
    private String uniqueId;

    public MyTableItemStaticSchema(UUID uniqueId) {
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

    public static final TableSchema<MyTableItemStaticSchema> STATIC_SCHEMA =
            TableSchema.builder(MyTableItemStaticSchema.class)
                    .newItemSupplier(MyTableItemStaticSchema::new)
                    .addAttribute(String.class, a -> a.name("PK")
                            .getter(MyTableItemStaticSchema::getPK)
                            .setter(MyTableItemStaticSchema::setPK)
                            .tags(StaticAttributeTags.primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("SK")
                            .getter(MyTableItemStaticSchema::getSK)
                            .setter(MyTableItemStaticSchema::setSK)
                            .tags(StaticAttributeTags.primarySortKey()))
                    .addAttribute(String.class, a -> a.name("uniqueId")
                            .getter(MyTableItemStaticSchema::getUniqueId)
                            .setter(MyTableItemStaticSchema::setUniqueId))
                    .build();
}
