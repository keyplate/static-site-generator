package com.lapchenko.generator.parser;

import java.util.List;

public record TextNode(String text, List<InlineFormat> format) {

}