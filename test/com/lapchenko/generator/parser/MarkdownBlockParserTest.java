package com.lapchenko.generator.parser;

import org.junit.jupiter.api.Test;
import java.util.List;
import com.lapchenko.generator.parser.MarkdownBlockParser;
import com.lapchenko.generator.parser.BlockFormat;

import static org.junit.jupiter.api.Assertions.*;

public class MarkdownBlockParserTest {

    private final static MarkdownBlockParser parser = new MarkdownBlockParser();

    //GENERAL
    @Test
    void blocksSplittedCorrectly() {
        var blocks = """
            ### This is heading blocks

            > This is
            > a
            > quote

            ```code block obviously```
            """;
        var expected = List.of(
            "### This is heading blocks",
            "> This is\n> a\n> quote",
            "```code block obviously```"
        );
        assertTrue(expected.containsAll(parser.markdownToBlocks(blocks)));
    }

    //HEADINGS
    @Test
    void headingBlock1() {
        var heading = "# Heading";
        var blocks = parser.blockToBlockTypes(List.of(heading));
        assertSame(BlockFormat.HEADING, blocks.get(heading));
    }

    @Test
    void headingBlock2() {
        var heading = "## Heading";
        var blocks = parser.blockToBlockTypes(List.of(heading));
        assertSame(BlockFormat.HEADING, blocks.get(heading));
    }

    @Test
    void headingBlockHashRedundant() {
        var heading = "####### Heading";
        var blocks = parser.blockToBlockTypes(List.of(heading));
        assertSame(BlockFormat.PARAGRAPH, blocks.get(heading));
    }

    //QUOTES
    @Test
    void quoteBlock() {
        var quote = """
            > This is a quote
            > This is also a quote
            """;
        var blocks = parser.blockToBlockTypes(List.of(quote));
        assertSame(BlockFormat.QUOTE, blocks.get(quote));
    }

    //CODE
    @Test
    void codeBlock() {
        var code = """
            ```
            This is a code block
            ```
            """;
        var blocks = parser.blockToBlockTypes(List.of(code));
        assertSame(BlockFormat.CODE, blocks.get(code));
    }

    @Test
    void codeBlockInline() {
        var code = "```{ var = This; is++; a code block}```";
        var blocks = parser.blockToBlockTypes(List.of(code));
        assertSame(BlockFormat.CODE, blocks.get(code));
    }

    //UNORDERED LIST
    void unorderedList() {
        var list = """
            * This
            * is
            * an item
            """;
        var blocks = parser.blockToBlockTypes(List.of(list));
        assertSame(BlockFormat.UNORDERED_LIST, blocks.get(list));
    }

    //ORDERED LIST
    void orderedList() {
        var list = """
            1 first
            2 second
            3 third
            """;
        var blocks = parser.blockToBlockTypes(List.of(list));
        assertSame(BlockFormat.ORDERED_LIST, blocks.get(list));
    }
}