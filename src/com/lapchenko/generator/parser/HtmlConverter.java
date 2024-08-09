package com.lapchenko.generator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlConverter {
    private MarkdownBlockParser blockParser;
    private MarkdownInlineParser inlineParser;

    public HtmlConverter(MarkdownBlockParser blockParser, MarkdownInlineParser inlineParser) {
        this.blockParser = blockParser;
        this.inlineParser = inlineParser;
    }

    public String markdownToHtml(String markdown) {
        var htmlNodes = new ArrayList<HtmlNode>();
        var blockTypes = blockParser.markdownToBlocks(markdown);
        var blocks = blockParser.blockToBlockTypes(blockTypes);
        for (var block : blocks.keySet()) {
            var blockType = blocks.get(block);
            if (blockType == BlockFormat.PARAGRAPH) {
                var inlineNodes = inlineParser.textToTextNodes(block);
                htmlNodes.addAll(inlineNodes.stream().map(this::textNodeToHtmlNode).toList());
            } else {
                var blockNode = blockToHmtlNode(block, blockType);
                htmlNodes.add(blockNode);
            }
        }
        return new HtmlNode("div", null, null, htmlNodes).toHtml();
    }

    public HtmlNode blockToHmtlNode(String text, BlockFormat format) {
        return switch (format) {
            case CODE -> toCode(text);
            case ORDERED_LIST -> toOderedList(text);
            case HEADING -> toHeading(text);
            case UNORDERED_LIST -> toUnorderedList(text);
            default -> toParagraph(text);
        };
    }

    public HtmlNode textNodeToHtmlNode(TextNode textNode) {
        return switch (textNode.format()) {
            case BOLD -> toBold(textNode);
            case ITALIC -> toItalic(textNode);
            case LINK -> toLink(textNode);
            case VIDEO -> toImage(textNode);
            default -> new HtmlNode("", null, textNode.text(), null);
        };
    }

    private HtmlNode toBold(TextNode textNode) {
        return new HtmlNode("b", null, textNode.text(), null);
    }

    private HtmlNode toItalic(TextNode textNode) {
        return new HtmlNode("i", null, textNode.text(), null);
    }

    private HtmlNode toLink(TextNode textnode) {
        return new HtmlNode("a",
            Map.of("href", "\"" + textnode.link() + "\""),
            null,
            null);
    }

    private HtmlNode toImage(TextNode textNode) {
        return new HtmlNode("img",
            Map.of("src", "\"" + textNode.link() + "\""),
            null,
            null
        );
    }

    private HtmlNode toParagraph(String text) {
        return new HtmlNode("p", null, text, null);
    }

    private HtmlNode toHeading(String text) {
        int level = 0;
        while (level < 6 && text.charAt(level) == '#') {
            level++;
        }
        var tag = "h" + level;
        var content = text.substring(level);
        return new HtmlNode(tag, null, content, null);
    }

    private HtmlNode toQuote(String text) {
        var sb = new StringBuilder();
        var entries = text.split("\n");
        for (var entry : entries) {
            sb.append(entry.substring(2)).append("\n");
        }
        return new HtmlNode("blockquote", null, sb.toString(), null);
    }

    private HtmlNode toCode(String text) {
        final var codeDelimeter = "```";
        var sb = new StringBuilder(text);
        sb.replace(0, codeDelimeter.length(), "");
        sb.replace(sb.length() - codeDelimeter.length(), sb.length(), "");
        var code = new HtmlNode("coed", null, sb.toString(), null);
        return new HtmlNode("pre", null, null, List.of(code));
    }

    private HtmlNode toUnorderedList(String text) {
        var entries = text.split("\n");
        var listItems = new ArrayList<HtmlNode>();
        for (String entry : entries) {
            listItems.add(
                new HtmlNode("<li>", null, entry.substring(2) + "\n", null)
            );
        }
        return new HtmlNode("ul", null, null, listItems);
    }

    private HtmlNode toOderedList(String text) {
        var entries = text.split("\n");
        var listItems = new ArrayList<HtmlNode>();
        for (String entry : entries) {
            listItems.add(
                new HtmlNode("<li>", null, entry.substring(entry.indexOf(" ")) + "\n", null)
            );
        }
        return new HtmlNode("ol", null, null, listItems);
    }
}