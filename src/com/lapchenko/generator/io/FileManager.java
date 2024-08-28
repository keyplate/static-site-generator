package com.lapchenko.generator.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        var files = getAllFilesPaths(destination);
        for (var file : files) {
            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                throw new RuntimeException(String.format("Problem deleting file %s occured", file));
            }
        }
    }

    public Map<Path, String> getAllFilesInDirectoryWithContent(Path path) {
        var fileMap = new HashMap<Path, String>();
        getAllFilesPaths(path).forEach(file -> fileMap.put(file, getFilesContent(file)));
        return fileMap;
    }

    public String getFilesContent(Path path) {
        try {
            return Files.lines(path).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Problem geting %s content", path));
        }
    }

    public List<Path> getAllFilesPaths(Path path) {
        var allFiles = new ArrayList<Path>();
        try (var files = Files.list(path)) {
            var paths = files.toList();
            for (var subPath : paths) {
                if (Files.isDirectory(subPath)) {
                    allFiles.addAll(getAllFilesPaths(subPath));
                } else {
                    allFiles.add(subPath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allFiles;
    }
}