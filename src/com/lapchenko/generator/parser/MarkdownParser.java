package com.lapchenko.generator.parser;

import com.lapchenko.generator.exception.UnevenDelimeterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarkdownParser {

    //todo add support for nesting
    public static List<TextNode> markdownToTextNode(String markdown) {
        return null;
    }

    public static List<TextNode> parseDelimeterNode(String text, InlineFormat format) {
        String[] nodes = text.split(format.getDelimeter());
        List<TextNode> parsedNodes = new ArrayList<>();

        if (nodes.length % 2 == 0) {
            throw new UnevenDelimeterException();
        }
        for (int i = 0; i < nodes.length; i++) {
            if (i % 2 == 0) {
                parsedNodes.add(new TextNode(nodes[i], List.of(InlineFormat.PLAIN)));
            } else {
                parsedNodes.add(new TextNode(nodes[i], List.of(format)));
            }
        }
        return parsedNodes;
    }
}