package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void writeToFile(String fileName, List<String> lines) throws IOException {
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        }
    }

    public static List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
