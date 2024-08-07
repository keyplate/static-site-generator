package com.lapchenko.generator.parser;

public enum MarkdownHtmlFormats {
    BOLD("\\*"),
    CODE("`"),
    ITALIC("\\*\\*"),
    PLAIN(null),
    STRIKETHROUGH("~~"),
    LINK("!\\[(.*?)\\]\\((.*?)\\)"),
    VIDEO("\\[(.*?)\\]\\((.*?)\\)");

    private final String delimeter;

    MarkdownHtmlFormats(String delimeter) {
        this.delimeter = delimeter;
    }

    public String getDelimeter() {
        return delimeter;
    }
}