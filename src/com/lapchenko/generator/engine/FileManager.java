package com.lapchenko.generator.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileManager {

    public void saveFiles(Map<Path, String> filesData) {
        for (var fileData : filesData.entrySet()) {
            try {
                Files.createFile(fileData.getKey());
                Files.write(fileData.getKey(), List.of(fileData.getValue()));
            } catch (IOException e) {
                throw new RuntimeException(String.format("Problem saving file %s", fileData.getKey()));
            }
        }
    }

    public void clearDestination(Path destination) {
        var files = getAllFiles(destination);
        for (var file : files) {
            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                throw new RuntimeException(String.format("Problem deleting file %s occured", file));
            }
        }
    }

    public List<Path> getAllFiles(Path path) {
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