package com.lapchenko.generator;

import java.nio.file.Path;
import com.lapchenko.generator.io.FileManager;
import com.lapchenko.generator.parser.HtmlConverter;
import com.lapchenko.generator.parser.MarkdownBlockParser;
import com.lapchenko.generator.parser.MarkdownInlineParser;

public class Main {
    public static void main(String[] args) {
        var processor = new MarkdownFileProcessor(new FileManager(),
                                                  new HtmlConverter(new MarkdownBlockParser(), new MarkdownInlineParser()),
                                                  Path.of(args[0]));
        processor.processFiles(Path.of(args[1]), Path.of(args[2]));
    }
}