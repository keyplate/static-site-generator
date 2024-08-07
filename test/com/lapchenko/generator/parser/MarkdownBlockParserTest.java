package com.lapchenko.generator.parser;

import org.junit.jupiter.api.Test;
import java.util.List;
import com.lapchenko.generator.parser.MarkdownBlockParser;
import com.lapchenko.generator.parser.BlockFormat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarkdownBlockParserTest {
    
    private final static MarkdownBlockParser parser = new MarkdownBlockParser(); 

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
    
    @Test
    void headingBlock() {
        var heading = "# Heading";
        var blocks = parser.blockToBlockTypes(List.of(heading));
        assertTrue(blocks.get(heading) == BlockFormat.HEADING);
    }
    
    @Test
    void quoteBlock() {
        var quote = """
        > This is a quote
        > This is also a quote
        """;
        var blocks = parser.blockToBlockTypes(List.of(quote));
        assertTrue(blocks.get(quote) == BlockFormat.QUOTE);
    }
}