package com.sqli.workshop.ddd.connaissance.client.domain.enums;

public enum SituationFamiliale {

    CELIBATAIRE("1"),

    MARIE("2");

    private final String value;

    SituationFamiliale(String value) {
        this.value = value;
    }

    public static SituationFamiliale fromValue(String text) {
        for (SituationFamiliale b : SituationFamiliale.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + text + "'");
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
