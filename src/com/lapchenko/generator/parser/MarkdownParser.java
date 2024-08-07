package com.lapchenko.generator.parser;

import com.lapchenko.generator.exception.UnevenDelimeterException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MarkdownParser {

    //todo add support for nesting
    public static List<TextNode> markdownToTextNode(String markdown) {
        return null;
    }

    public static List<TextNode> parseInlineFormat(List<TextNode> oldNodes, InlineFormat format) {
        var newNodes = new ArrayList<>(oldNodes.stream()
            .filter(node -> node.format() == InlineFormat.PLAIN)
            .toList());

        oldNodes.removeAll(newNodes);
        newNodes.forEach(node -> {
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

    public static List<TextNode> parseLinks(List<TextNode> nodes) {
        var linkRegex = Pattern.compile("\[(?<text>[^\]]*)\]\((?<link>[^\)]*)\)");
        return nodes.forEach(node -> {

        });
    }

    public static List<TextNode> parseBlockFormat() {
    }
}