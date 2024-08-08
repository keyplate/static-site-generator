package com.lapchenko.generator.parser;

import com.lapchenko.generator.exception.UnevenDelimeterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MarkdownInlineParser {

    public List<TextNode> textToTextNodes(String text) {
        var initialNode = new TextNode(text, InlineFormat.PLAIN);
        List<TextNode> nodes = new ArrayList<>();
        nodes.add(initialNode);
        nodes = parseInlineFormat(nodes, InlineFormat.ITALIC);
        nodes = parseInlineFormat(nodes, InlineFormat.BOLD);
        nodes = parseInlineFormat(nodes, InlineFormat.STRIKETHROUGH);
        nodes = parseLinks(nodes);
        nodes = parseVideo(nodes);
        return nodes;
    }
    public List<TextNode> parseInlineFormat(List<TextNode> oldNodes, InlineFormat format) {
        var newNodes = new ArrayList<TextNode>();
        for (TextNode node : oldNodes) {
            if (node.format() == InlineFormat.PLAIN) {
                var nodeTextDelimiter = node.text().split(format.getDelimeter(), -1);
                if (nodeTextDelimiter.length % 2 == 0) {
                    throw new UnevenDelimeterException("Formatting incorrect here: " + node.text());
                }
                for (int i = 0; i < nodeTextDelimiter.length; i++) {
                    var newFormat = (i % 2 == 0) ? InlineFormat.PLAIN : format;
                    newNodes.add(new TextNode(nodeTextDelimiter[i], newFormat));
                }
            } else {
                newNodes.add(node);
            }
        }
        return newNodes;
    }

    public List<TextNode> parseLinks(List<TextNode> oldNodes) {
        var newNodes = new ArrayList<TextNode>();
        for (TextNode node : oldNodes) {
            if (node.format() == InlineFormat.PLAIN) {
                newNodes.addAll(extractLinks(node, InlineFormat.LINK));
            } else {
                newNodes.add(node);
            }
        }
        return newNodes;
    }


    public List<TextNode> parseVideo(List<TextNode> oldNodes) {
        var newNodes = new ArrayList<TextNode>();
        for (TextNode node : oldNodes) {
            if (node.format() == InlineFormat.PLAIN) {
                newNodes.addAll(extractLinks(node, InlineFormat.VIDEO));
            } else {
                newNodes.add(node);
            }
        }
        return newNodes;
    }

    public List<TextNode> extractLinks(TextNode node, InlineFormat format) {
        var newNodes = new ArrayList<TextNode>();
        var matcher = Pattern.compile(format.getDelimeter()).matcher(node.text());
        var remainingText = node.text();
        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                newNodes.add(new TextNode(remainingText.substring(lastEnd, matcher.start()), InlineFormat.PLAIN));
            }
            newNodes.add(new TextNode(matcher.group(1), format, matcher.group(2)));
            lastEnd = matcher.end();
        }
        if (lastEnd < remainingText.length()) {
            newNodes.add(new TextNode(remainingText.substring(lastEnd), InlineFormat.PLAIN));
        }
        return newNodes;
    }
}