package com.lapchenko.generator.parser;

import com.lapchenko.generator.exception.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MarkdownLinkParserTest {

    @Test
    void regularVideo() {
        var videoLink = new ArrayList<TextNode>();
        videoLink.add(new TextNode("[video-name](video-link)",
            MarkdownHtmlFormats.PLAIN));
        var expected = new TextNode("video-name",
            MarkdownHtmlFormats.VIDEO,
            "video-link");
        var parser = new MarkdownParser();

        assertEquals(expected, parser.parseVideo(videoLink).getFirst());
    }

    @Test
    void videoInsideText() {
        var videoLink = new ArrayList<TextNode>();
        videoLink.add(new TextNode("Here's some text [video-name](video-link) text",
            MarkdownHtmlFormats.PLAIN));
        var expected = List.of(
            new TextNode("Here's some text ", MarkdownHtmlFormats.PLAIN),
            new TextNode("video-name", MarkdownHtmlFormats.VIDEO, "video-link"),
            new TextNode(" text", MarkdownHtmlFormats.PLAIN)
        );
        var parser = new MarkdownParser();

        assertTrue(expected.containsAll(parser.parseVideo(videoLink)));
    }

    @Test
    void videoInsideTextWithoutSpacing() {
        var videoLink = new ArrayList<TextNode>();
        videoLink.add(new TextNode("Here's some text[video-name](video-link)text",
            MarkdownHtmlFormats.PLAIN));
        var expected = List.of(
            new TextNode("Here's some text", MarkdownHtmlFormats.PLAIN),
            new TextNode("video-name", MarkdownHtmlFormats.VIDEO, "video-link"),
            new TextNode("text", MarkdownHtmlFormats.PLAIN)
        );
        var parser = new MarkdownParser();

        assertTrue(expected.containsAll(parser.parseVideo(videoLink)));
    }

    @Test
    void multipleVideo() {
        var videoLink = new ArrayList<TextNode>();
        videoLink.add(new TextNode("Here's some text[video-name](video-link)text [video-name](video-link) ",
            MarkdownHtmlFormats.PLAIN));
        var expected = List.of(
            new TextNode("Here's some text", MarkdownHtmlFormats.PLAIN),
            new TextNode("video-name", MarkdownHtmlFormats.VIDEO, "video-link"),
            new TextNode("text ", MarkdownHtmlFormats.PLAIN),
            new TextNode("video-name", MarkdownHtmlFormats.VIDEO, "video-link")
        );
        var parser = new MarkdownParser();

        assertTrue(expected.containsAll(parser.parseVideo(videoLink)));
    }
}