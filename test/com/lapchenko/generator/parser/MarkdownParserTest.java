package com.lapchenko.generator.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lapchenko.generator.parser.*;
import com.lapchenko.generator.exception.*;

import java.util.List;


public class MarkdownParserTest {

    @Test
    void boldParsing() {
        var bold = new TextNode("Hello *world*!", InlineFormat.PLAIN);

        var expected = new TextNode("world", InlineFormat.BOLD);
        var actual = MarkdownParser.parseDelimeterNode(bold, InlineFormat.BOLD);

        assertEquals(expected, actual.get(1));
    }

    @Test
    void italicParsing() {
        var italic = new TextNode("Hello **world**!", InlineFormat.PLAIN);

        var expected = new TextNode("world", InlineFormat.ITALIC);
        var actual = MarkdownParser.parseDelimeterNode(italic, InlineFormat.ITALIC);

        assertEquals(expected, actual.get(1));
    }

    @Test
    void delimitersImbalanced() {
        var imbalanced = new TextNode("Hello **world*!", InlineFormat.PLAIN);
        assertThrows(UnevenDelimeterException.class,
            () -> MarkdownParser.parseDelimeterNode(imbalanced, InlineFormat.ITALIC));
    }
}