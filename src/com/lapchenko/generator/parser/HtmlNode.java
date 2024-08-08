
package com.lapchenko.generator.parser;

import java.util.List;
import java.util.Map;

import static com.lapchenko.generator.parser.InlineFormat.*;
import static com.lapchenko.generator.parser.BlockFormat.*;

public class HtmlNode {
    private String tag;
    private Map<String, String> attributes;
    private String value;
    private List<HtmlNode> children;
    
    public HtmlNode(String tag, Map<String, String> attributes, String value, List<HtmlNode> children) {
      this.tag = tag;
      this.attributes = attributes;
      this.value = value;
      this.children = children;
    }
    
    public String toHtml() {
        if (children == null || children.isEmpty()) {
            var value = this.value == null? "" : this.value;
            return tagWithAttributes() + value + enclosingTag();
        }
        var stringBuilder = new StringBuilder();
        stringBuilder.append(tagWithAttributes());
        for (var child : children) {
            stringBuilder.append(child.toHtml());
        }
        stringBuilder.append(enclosingTag());
        return stringBuilder.toString();
    }
    
    private String tagWithAttributes() {
        if (attributes == null || attributes.isEmpty()) {
            return "<" + this.tag + ">";
        }
        var stringBuilder = new StringBuilder();
        stringBuilder.append("<").append(this.tag);
        for (var entry : attributes.entrySet()) {
            stringBuilder.append(" ").append(entry.getKey()).append("=").append(entry.getValue());
        }
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
    
    private String enclosingTag() {
        return "</" + this.tag + ">";
    }
}