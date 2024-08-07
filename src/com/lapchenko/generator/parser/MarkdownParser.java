package com.lapchenko.generator.parser;

import com.lapchenko.generator.exception.UnevenDelimeterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class MarkdownParser {

    public List<TextNode> markdownToTextNode(String markdown) {
        return null;
    }

    public List<TextNode> parseInlineFormat(List<TextNode> oldNodes, MarkdownHtmlFormats format) {
        var nodesToParse = new ArrayList<>(oldNodes.stream()
            .filter(node -> node.format() == MarkdownHtmlFormats.PLAIN)
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
                    newNodes.add(new TextNode(nodeTextDelimiter[i], MarkdownHtmlFormats.PLAIN));
                } else {
                    newNodes.add(new TextNode(nodeTextDelimiter[i], format));
                }
            }
        });
        newNodes.addAll(oldNodes);

        return newNodes;
    }

    public List<TextNode> parseLinks(List<TextNode> nodes) {
        var newNodes = new ArrayList<TextNode>();
        final var linkRegex = MarkdownHtmlFormats.LINK.getDelimeter();
        nodes.forEach(node -> newNodes.addAll(extractLinks(node, MarkdownHtmlFormats.LINK)));
        return newNodes;
    }

    public List<TextNode> parseVideo(List<TextNode> nodes) {
        var newNodes = new ArrayList<TextNode>();
        final var linkRegex = MarkdownHtmlFormats.VIDEO.getDelimeter();
        nodes.forEach(node -> newNodes.addAll(extractLinks(node, MarkdownHtmlFormats.VIDEO)));
        return newNodes;
    }

    public List<TextNode> extractLinks(TextNode node, MarkdownHtmlFormats format) {
        var newNodes = new ArrayList<TextNode>();
        var matcher = Pattern.compile(format.getDelimeter()).matcher(node.text());
        var text = node.text();

        while (matcher.find()) {
            var link = matcher.group();
            var textAroundLink = text.split(Pattern.quote(link));
            if (textAroundLink.length > 1 && !Objects.equals(textAroundLink[0], "")) {
                newNodes.add(new TextNode(textAroundLink[0], MarkdownHtmlFormats.PLAIN));
                text = textAroundLink[1];
            }
            newNodes.add(new TextNode(matcher.group(1), format, matcher.group(2)));
        }
        return newNodes;
    }
}