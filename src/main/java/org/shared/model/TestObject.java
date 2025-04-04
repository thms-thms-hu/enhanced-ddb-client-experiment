package org.shared.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TestObject {
    private final String string;

    public TestObject(String string) {
        this.string = string;
    }
}
