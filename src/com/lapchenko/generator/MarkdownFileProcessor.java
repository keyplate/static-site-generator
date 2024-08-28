package com.lapchenko.generator;

import com.lapchenko.generator.io.FileManager;
import com.lapchenko.generator.parser.HtmlConverter;

import java.nio.file.Path;
import java.util.HashMap;

public class MarkdownFileProcessor {
    
    private FileManager fileManager;
    private HtmlConverter converter;
    private final String template;
    private final static String TEMPLAE_PLACEHOLDER = "{{ Content }}";

    public MarkdownFileProcessor(FileManager fileManager, HtmlConverter converter, Path templateFile) {
        this.fileManager = fileManager;
        this.converter = converter;
        this.template = fileManager.getFilesContent(templateFile);
    }


    public void processFiles(Path inputDirectory, Path outputDirectory) {
        var fileContentAndPaths = fileManager.getAllFilesInDirectoryWithContent(inputDirectory);
        var htmlFilesAndPaths = new HashMap<Path, String>();
        fileManager.clearDestination(outputDirectory);
        for (var fileEntry : fileContentAndPaths.entrySet()) {
            var fileContent = converter.markdownToHtml(fileEntry.getValue());
            if (fileEntry.getKey().toString().contains(".md")) {
                fileContent = applyTemplate(fileContent);
            }
            htmlFilesAndPaths.put(adjustPath(fileEntry.getKey(), outputDirectory), fileContent);
        }
        fileManager.saveFiles(htmlFilesAndPaths);
    }
    
    private String applyTemplate(String html) {
        System.out.println(html);
        return template.replace(TEMPLAE_PLACEHOLDER, html); 
    }
    
    private Path adjustPath(Path filePath, Path outputDirectory) {
        if (filePath.toString().contains(".md")) {
            var fileName = filePath.getFileName().toString();
            return Path.of(outputDirectory.toString(), fileName.replace(".md", ".html"));
        }
        return Path.of(outputDirectory.toString(), filePath.getFileName().toString());
    }
}