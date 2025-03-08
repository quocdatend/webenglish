package com.webenglish.webenglish.Common;

public enum Role {
    ADMIN(1),
    USER(2),
    PRE(3);
    public final long value;
    // Explicit constructor
    Role(long value) {
        this.value = value;
    }
}
