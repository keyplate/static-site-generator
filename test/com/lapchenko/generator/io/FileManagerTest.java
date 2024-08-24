package com.lapchenko.generator.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.stream.Collectors;

import com.lapchenko.generator.io.FileManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileManagerTest {
    private final static String basePath = "./testing";
    
    @BeforeEach
    void clearBaseDirectory() throws IOException {
        var path = Path.of(basePath);
        if (Files.exists(path)) {
            Files.walkFileTree(path, new DeletingVisitor());   
        }
        Files.createDirectory(path);
    }
    
    @Test
    void allFilesRetrieved() throws IOException {
        var fileManager = new FileManager();
        var expected = List.of(Path.of(basePath, "a.txt"),
                                   Path.of(basePath, "b.txt"),
                                   Path.of(basePath, "a", "c.txt"));
        Files.createFile(Path.of(basePath, "a.txt"));
        Files.createFile(Path.of(basePath, "b.txt"));
        Files.createDirectory(Path.of(basePath, "a"));
        Files.createFile(Path.of(basePath, "a", "c.txt"));
        
        var actual = fileManager.getAllFiles(Path.of(basePath));
        assertTrue(actual.containsAll(expected));
    }
    
    @Test
    void noFilesRetrieved() throws IOException {
        var fileManager = new FileManager();
        assertTrue(fileManager.getAllFiles(Path.of(basePath)).isEmpty());
    }
    
    @Test
    void allFilesCreated() throws IOException {
        var fileManager = new FileManager();
        var pathA = Path.of(basePath, "a.txt");
        var pathB = Path.of(basePath, "b.txt");
        var expected = List.of(pathA, pathB);
        var filesData = Map.of(
                pathA, "#Header\n\n*bold*",
                pathB, "```code```");
        fileManager.saveFiles(filesData);
        var actual = Files.list(Path.of(basePath)).toList();
        assertTrue(expected.containsAll(actual));
    }
    
    @Test
    void contentMatches() throws IOException {
        var fileManager = new FileManager();
        var fileContent = "# Heading\n\n**body**";
        var expected = List.of("# Heading", "", "**body**");
        var filePath = Path.of(basePath, "a.txt");
        var filesData = Map.of(filePath, fileContent);
        fileManager.saveFiles(filesData);
        assertTrue(Files.lines(filePath).toList().containsAll(expected));
    }
    
    static class DeletingVisitor extends SimpleFileVisitor<Path> {
        
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.deleteIfExists(file);
            return FileVisitResult.CONTINUE;
        }
    
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}