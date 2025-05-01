import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileCollector {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java FileCollector <input_dir> <output_dir>");
            return;
        }

        File inputDir = new File(args[0]);
        File outputDir = new File(args[1]);
        if (!outputDir.exists()) outputDir.mkdirs();

        Map<String, Integer> nameCounts = new HashMap<>();
        copyFilesRecursively(inputDir, outputDir, nameCounts);
    }

    private static void copyFilesRecursively(File currentDir, File outputDir, Map<String, Integer> nameCounts) throws IOException {
        File[] files = currentDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                copyFilesRecursively(file, outputDir, nameCounts);
            } else {
                String fileName = file.getName();
                String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
                String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.')) : "";
                String finalName = fileName;

                // Проверка повторов
                if (nameCounts.containsKey(fileName)) {
                    int count = nameCounts.get(fileName) + 1;
                    nameCounts.put(fileName, count);
                    finalName = baseName + "_" + count + extension;
                } else {
                    nameCounts.put(fileName, 0);
                }

                Path targetPath = new File(outputDir, finalName).toPath();
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Copied: " + file.getAbsolutePath() + " -> " + targetPath);
            }
        }
    }
}
