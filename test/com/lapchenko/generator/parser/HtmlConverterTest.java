package com.lapchenko.generator.parser;

import com.lapchenko.generator.parser.HtmlConverter;
import com.lapchenko.generator.parser.TextNode;
import com.lapchenko.generator.parser.HtmlNode;
import com.lapchenko.generator.parser.InlineFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlConverterTest {
    
    @Test
    void boldTextToHtml() {
        var bold = new TextNode("Bold text", InlineFormat.BOLD, null);
        var expected = new HtmlNode("b", null, "Bold text", null);
        var converter = new HtmlConverter();
        assertEquals(expected, converter.textNodeToHtmlNode(bold));
    }
    
    @Test
    void plainTextToHtml() {
        var plain = new TextNode("Some text", InlineFormat.PLAIN, null);
        var expected = new HtmlNode("", null, "Some text", null);
        var converter = new HtmlConverter();
        assertEquals(expected, converter.textNodeToHtmlNode(plain));
    }
}