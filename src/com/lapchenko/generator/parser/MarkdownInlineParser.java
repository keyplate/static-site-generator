package com.lapchenko.generator.parser;

import com.lapchenko.generator.exception.UnevenDelimeterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        var nodesToParse = new ArrayList<TextNode>(oldNodes.stream()
            .filter(node -> node.format() == InlineFormat.PLAIN)
            .toList());
        oldNodes.removeAll(nodesToParse);
        var newNodes = new ArrayList<TextNode>();
        nodesToParse.forEach(node -> {
            var nodeTextDelimiter = node.text().split(format.getDelimeter(), -1);
            if (nodeTextDelimiter.length % 2 == 0) {
                throw new UnevenDelimeterException();
            }
            for (int i = 0; i < nodeTextDelimiter.length; i++) {
                if (i % 2 == 0) {
                    newNodes.add(new TextNode(nodeTextDelimiter[i], InlineFormat.PLAIN));
                } else {
                    newNodes.add(new TextNode(nodeTextDelimiter[i], format));
                }
            }
        });
        newNodes.addAll(oldNodes);
        return newNodes;
    }

    public List<TextNode> parseLinks(List<TextNode> oldNodes) {
        var nodesToParse = new ArrayList<TextNode>(oldNodes.stream()
            .filter(node -> node.format() == InlineFormat.PLAIN)
            .toList());
        oldNodes.removeAll(nodesToParse);
        var newNodes = new ArrayList<TextNode>();
        nodesToParse.forEach(node -> newNodes.addAll(extractLinks(node, InlineFormat.LINK)));
        newNodes.addAll(oldNodes);
        return newNodes;
    }

    public List<TextNode> parseVideo(List<TextNode> oldNodes) {
        var nodesToParse = new ArrayList<TextNode>(oldNodes.stream()
            .filter(node -> node.format() == InlineFormat.PLAIN)
            .toList());
        oldNodes.removeAll(nodesToParse);
        var newNodes = new ArrayList<TextNode>();
        nodesToParse.forEach(node -> newNodes.addAll(extractLinks(node, InlineFormat.VIDEO)));
        newNodes.addAll(oldNodes);
        return newNodes;
    }

    public List<TextNode> extractLinks(TextNode node, InlineFormat format) {
        List<TextNode> newNodes = new ArrayList<>();
        var matcher = Pattern.compile(format.getDelimeter()).matcher(node.text());
        String remainingText = node.text();
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