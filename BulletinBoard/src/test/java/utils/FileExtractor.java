package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileExtractor {

    public static String getJsonFromFile(String path) {
        File file = new File(path);
        try {
            return FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException("File could not be read!");
        }
    }
}
