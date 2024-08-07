package com.lapchenko.generator.parser;

import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MarkdownBlockParser {
    
    public List<String> markdownToBlocks(String markdown) {
        var blocks = markdown.split("\n\n");
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = blocks[i].trim();
        }
        return Arrays.asList(blocks);
    }
    
    public Map<String, BlockFormat> blockToBlockTypes(List<String> blocks) {
        var blockTypes = new LinkedHashMap<String, BlockFormat>();
        for (var block : blocks) {
            if (isHeading(block)) { blockTypes.put(block, BlockFormat.HEADING); }
            else if (isCode(block)) { blockTypes.put(block, BlockFormat.CODE); }
            else if (isQuote(block)) { blockTypes.put(block, BlockFormat.QUOTE); }
            else if (isUnorderedList(block)) { blockTypes.put(block, BlockFormat.UNORDERED_LIST); }
            else if (isOrderedList(block)) { blockTypes.put(block, BlockFormat.ORDERED_LIST); }
            else { blockTypes.put(block, BlockFormat.PARAGRAPH); }
        }
        return blockTypes;
    }
    
    private boolean isHeading(String block) {
        return block.strip().matches(BlockFormat.HEADING.getRegex());
    }
    
    private boolean isCode(String block) {
        return block.strip().matches(BlockFormat.CODE.getRegex());
    }
    
    private boolean isQuote(String block) {
        var regex = BlockFormat.QUOTE.getRegex();
        var quoteLines = block.strip().split("\\n");
        return Arrays.stream(quoteLines).allMatch(line -> line.matches(regex));
    }
    
    private boolean isUnorderedList(String block) {
        var regex = BlockFormat.UNORDERED_LIST.getRegex();
        var unorderedListLines = block.strip().split("\\n");
        return Arrays.stream(unorderedListLines).allMatch(line -> line.matches(regex));
    }
    
    private boolean isOrderedList(String block) {
        var regex = BlockFormat.ORDERED_LIST.getRegex();
        var orderedListLines = block.strip().split("\\n");
        return Arrays.stream(orderedListLines).allMatch(line -> line.matches(regex));
    }
}