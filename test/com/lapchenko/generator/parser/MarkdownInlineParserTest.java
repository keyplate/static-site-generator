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
        bold.add(new TextNode("Hello *world*!", InlineFormat.PLAIN));
        var parser = new MarkdownInlineParser();

        var expected = new TextNode("world", InlineFormat.BOLD);
        var actual = parser.parseInlineFormat(bold, InlineFormat.BOLD);

        assertEquals(expected, actual.get(1));
    }

    @Test
    void italicParsing() {
        var italic = new ArrayList<TextNode>();
        italic.add(new TextNode("Hello **world**!", InlineFormat.PLAIN));
        var parser = new MarkdownInlineParser();

        var expected = new TextNode("world", InlineFormat.ITALIC);
        var actual = parser.parseInlineFormat(italic, InlineFormat.ITALIC);

        assertEquals(expected, actual.get(1));
    }

    @Test
    void delimitersImbalanced() {
        var imbalanced = new ArrayList<TextNode>();
        imbalanced.add(new TextNode("Hello **world*!", InlineFormat.PLAIN));
        var parser = new MarkdownInlineParser();

        assertThrows(UnevenDelimeterException.class,
            () -> parser.parseInlineFormat(imbalanced, InlineFormat.ITALIC));
    }
}