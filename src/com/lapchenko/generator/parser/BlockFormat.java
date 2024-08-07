package com.lapchenko.generator.parser;

public enum BlockFormat {
    QUOTE("^>\\s+(.*)$"),
    CODE("^`{3}(.|\\n)*`{3}"),
    UNORDERED_LIST("^\\*\\s+(.*)$"),
    ORDERED_LIST("^\\d+\\)\\s+(.*)$"),
    HEADING("^#{1,6}\\s+(.+)$"),
    PARAGRAPH("[\\s\\S]");
    
    private final String regex;


    BlockFormat(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}