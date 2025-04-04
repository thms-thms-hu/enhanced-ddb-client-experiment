package org.shared.model.utils;

import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SchemaUtil {

    public static <T> StaticTableSchema<T> generateSchema(Class<T> c) {

        StaticTableSchema.Builder<T> builder = TableSchema.builder(c);
        Constructor<T> constructor;

        try {
            constructor = c.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Error calling constructor");
        }

        builder.newItemSupplier(() -> {
            try {
                return constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error creating schema");
            }
        });

        for (Method method : c.getDeclaredMethods()) {

            if (method.getAnnotation(DynamoDbPartitionKey.class) != null) {
                System.out.println("PK");

                builder.addAttribute(String.class, a -> a.name("PK")
                        .getter(createGetter(c, method))
                        .setter(createSetter(c, method))
                        .tags(StaticAttributeTags.primaryPartitionKey()));

            } else if (method.getAnnotation(DynamoDbSortKey.class) != null) {
                System.out.println("SK");

                builder.addAttribute(String.class, a -> a.name("SK")
                        .getter(createGetter(c, method))
                        .setter(createSetter(c, method))
                        .tags(StaticAttributeTags.primarySortKey()));

            } else if (method.getAnnotation(DynamoDbAttribute.class) != null) {
                System.out.println("Attribute");

                builder.addAttribute(String.class, a -> a.name(method.getAnnotation(DynamoDbAttribute.class).value())
                        .getter(createGetter(c, method))
                        .setter(createSetter(c, method))
                );
            }
        }

        return builder.build();

    }

    private static <T> Function<T, String> createGetter(Class<T> c, Method method) {
        return item -> {
            try {
                return (String) method.invoke(item);
            } catch (Exception e) {
                throw new RuntimeException("Error creating getter for schema");
            }
        };
    }

    private static <T> BiConsumer<T, String> createSetter(Class<T> c, Method method) {
        return (item, value) -> {
            try {
                method.invoke(item, value);
            } catch (Exception e) {
                throw new RuntimeException("Error creating setter for schema");
            }
        };
    }
}
