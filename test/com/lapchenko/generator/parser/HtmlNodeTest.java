package com.lapchenko.generator.parser;

import com.lapchenko.generator.parser.HtmlNode;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlNodeTest {
    
    @Test
    void boldTag() {
        var bold = new HtmlNode("b", null, "bold", null);
        var expected = "<b>bold</b>";
        assertEquals(expected, bold.toHtml());
    }
    
    @Test
    void divWithChildren() {
        var children = List.of(
                new HtmlNode("b", null, "bold", null),
                new HtmlNode("p", null, "paragraph", null)
                );
        var parent = new HtmlNode("b", null, null, children);
        var expected = "<b><b>bold</b><p>paragraph</p></b>";
        assertEquals(expected, parent.toHtml());
    }
    
    @Test
    void divWithAttributes() {
        var div = new HtmlNode("div", Map.of("id", "\"tag\"", "hidden", "\"true\""), null, null);
        var expectedStart = "<div";
        var expectedContains1 = "id=\"tag\"";
        var expectedContains2 =  "hidden=\"true\"";
        var expectedEnd = "></div>";
        assertTrue(div.toHtml().contains(expectedContains1));
        assertTrue(div.toHtml().contains(expectedContains2));
        assertTrue(div.toHtml().startsWith(expectedStart));
        assertTrue(div.toHtml().endsWith(expectedEnd));
    }
}