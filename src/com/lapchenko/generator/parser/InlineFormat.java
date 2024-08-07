package com.lapchenko.generator.parser;

public enum InlineFormat {
    BOLD("\\*"),
    CODE("`"),
    ITALIC("\\*\\*"),
    PLAIN(null),
    STRIKETHROUGH("~~");

    private final String delimeter;

    InlineFormat(String delimeter) {
        this.delimeter = delimeter;
    }

    public String getDelimeter() {
        return delimeter;
    }
}