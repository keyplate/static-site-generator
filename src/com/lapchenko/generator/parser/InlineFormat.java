package com.lapchenko.generator.parser;

public enum InlineFormat {
    BOLD("\\*"),
    CODE("`"),
    ITALIC("\\*\\*"),
    PLAIN(null),
    STRIKETHROUGH("~~"),
    LINK("\\[(.*?)\\]\\((.*?)\\)"),
    VIDEO("!\\[(.*?)\\]\\((.*?)\\)");

    private final String delimeter;

    InlineFormat(String delimeter) {
        this.delimeter = delimeter;
    }

    public String getDelimeter() {
        return delimeter;
    }
}