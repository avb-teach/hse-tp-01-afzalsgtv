import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java FileCollector <input_dir> <output_dir> [--max_depth N]");
            return;
        }

        File inputDir = new File(args[0]);
        File outputDir = new File(args[1]);
        if (!outputDir.exists()) outputDir.mkdirs();

        int maxDepth = parseMaxDepth(args);
        Map<String, Integer> nameCounts = new HashMap<>();
        copyFilesRecursively(inputDir, outputDir, nameCounts, 0, maxDepth);
    }

    private static int parseMaxDepth(String[] args) {
        for (int i = 2; i < args.length - 1; i++) {
            if (args[i].equals("--max_depth")) {
                try {
                    return Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value for --max_depth");
                    return -1;
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private static void copyFilesRecursively(File currentDir, File outputDir, Map<String, Integer> nameCounts, int currentDepth, int maxDepth) throws IOException {
        if (currentDepth > maxDepth) return;
        File[] files = currentDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                copyFilesRecursively(file, outputDir, nameCounts, currentDepth + 1, maxDepth);
            } else {
                String fileName = file.getName();
                String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
                String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.')) : "";
                String finalName = fileName;

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
