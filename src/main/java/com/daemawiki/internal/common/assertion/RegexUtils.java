package com.daemawiki.internal.common.assertion;

public enum RegexUtils {

    PASSWORD("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=,.<>?/])[a-zA-Z\\d!@#$%^&*()_+=,.<>?/]"),

    DSM_EMAIL("^[a-zA-Z0-9._%+-]+@dsm\\.hs\\.kr"),

    ALLOWED_ONLY_KOR("^[가-힣]+"),

    ALLOWED_ONLY_ENG("^[a-zA-Z]+"),

    ALLOWED_ONLY_KOR_N_ENG("^[a-zA-Z가-힣]+"),

    ALLOWED_ONLY_ENG_N_NUM("^[a-zA-Z0-9]+");

    private final String regex;

    public String getRegex() {
        return regex + "$";
    }

    public String getRegex(
            final int minLength,
            final int maxLength
    ) {
        return new StringBuilder(regex)
                .append('{')
                .append(minLength)
                .append(',')
                .append(maxLength)
                .append("}$")
                .toString();
    }

    RegexUtils(final String regex) {
        this.regex = regex;
    }

}