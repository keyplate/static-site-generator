package com.lapchenko.generator.parser;

public record TextNode(String text, InlineFormat format, String link) {
    public TextNode(String text, InlineFormat format) {
        this(text, format, null);
    }
}