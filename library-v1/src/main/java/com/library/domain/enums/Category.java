package com.library.domain.enums;

public enum Category {
    FANTASIA("Fantasia"),
    SUSPENSE("Suspense"),
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    TERROR("Terror");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription(){
        return displayName;
    }
}