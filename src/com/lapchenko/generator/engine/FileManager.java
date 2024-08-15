package com.lapchenko.generator.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String workDirPath;
    private final String contentDirPath;

    public FileManager(String path, String contenDirPath) {
        this.workDirPath = path;
        this.contentDirPath = contenDirPath;
    }

    private void copyFiles() {
        var files = getAllFiles(Path.of(contentDirPath));
        for (var file : files) {
            try {
                Files.copy(file, Path.of(workDirPath));
            } catch (IOException e) {
                throw new RuntimeException(String.format("Problem copying file %s to location %s occured", file, workDirPath));
            }
        }
    }

    private void clearContent() {
        var files = getAllFiles(Path.of(this.workDirPath));
        for (var file : files) {
            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                throw new RuntimeException(String.format("Problem deleting file %s occured", file));
            }
        }
    }

    private List<Path> getAllFiles(Path path) {
        var allFiles = new ArrayList<Path>();
        try (var files = Files.list(path)) {
            var paths = files.toList();
            for (var subPath : paths) {
                if (Files.isDirectory(subPath)) {
                    allFiles.addAll(getAllFiles(subPath));
                } else {
                    allFiles.add(path);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allFiles;
    }
}