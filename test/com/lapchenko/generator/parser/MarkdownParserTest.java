package com.lapchenko.generator.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lapchenko.generator.parser.*;

import java.util.List;


public class MarkdownParserTest {

    @Test
    void boldParsing() {
        String bold = "Hello *world*!";
        TextNode expected = new TextNode("world", List.of(InlineFormat.BOLD));
        List<TextNode> nodes = MarkdownParser.parseDelimeterNode(bold, InlineFormat.BOLD);
        assertEquals(expected, nodes.get(1));
    }

    @Test
    void italicParsing() {
        String italic = "Hello **world**!";
        TextNode expected = new TextNode("world", List.of(InlineFormat.ITALIC));
        List<TextNode> nodes = MarkdownParser.parseDelimeterNode(italic, InlineFormat.ITALIC);
        assertEquals(expected, nodes.get(1));
    }
}