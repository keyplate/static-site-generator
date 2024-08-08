package com.lapchenko.generator.parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        if (tag == null || tag.isEmpty()) {
            return value;
        }
        if (children == null || children.isEmpty()) {
            var value = this.value == null ? "" : this.value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HtmlNode htmlNode = (HtmlNode) o;

        if (!Objects.equals(tag, htmlNode.tag)) return false;
        if (!Objects.equals(attributes, htmlNode.attributes)) return false;
        if (!Objects.equals(value, htmlNode.value)) return false;
        return Objects.equals(children, htmlNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, attributes, value, children);
    }
}