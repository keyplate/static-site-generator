package com.lapchenko.generator.parser;

public record TextNode(String text, MarkdownHtmlFormats format, String link) {
    public TextNode(String text, MarkdownHtmlFormats format) {
        this(text, format, null);
    }
}