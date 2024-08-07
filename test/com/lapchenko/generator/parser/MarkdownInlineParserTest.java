package com.lapchenko.generator.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lapchenko.generator.exception.*;

import java.util.ArrayList;

public class MarkdownInlineParserTest {

    @Test
    void boldParsing() {
        var bold = new ArrayList<TextNode>();
        bold.add(new TextNode("Hello *world*!", MarkdownHtmlFormats.PLAIN));
        var parser = new MarkdownParser();

        var expected = new TextNode("world", MarkdownHtmlFormats.BOLD);
        var actual = parser.parseInlineFormat(bold, MarkdownHtmlFormats.BOLD);

        assertEquals(expected, actual.get(1));
    }

    @Test
    void italicParsing() {
        var italic = new ArrayList<TextNode>();
        italic.add(new TextNode("Hello **world**!", MarkdownHtmlFormats.PLAIN));
        var parser = new MarkdownParser();

        var expected = new TextNode("world", MarkdownHtmlFormats.ITALIC);
        var actual = parser.parseInlineFormat(italic, MarkdownHtmlFormats.ITALIC);

        assertEquals(expected, actual.get(1));
    }

    @Test
    void delimitersImbalanced() {
        var imbalanced = new ArrayList<TextNode>();
        imbalanced.add(new TextNode("Hello **world*!", MarkdownHtmlFormats.PLAIN));
        var parser = new MarkdownParser();

        assertThrows(UnevenDelimeterException.class,
            () -> parser.parseInlineFormat(imbalanced, MarkdownHtmlFormats.ITALIC));
    }
}