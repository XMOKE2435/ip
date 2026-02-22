package xmoke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Handles loading tasks from disk and saving tasks to disk in a simple text-based format.
 */

public class Storage {
    private static final Path DATA_FOLDER_PATH = Paths.get("data");
    private static final Path DATA_FILE_PATH = DATA_FOLDER_PATH.resolve("XMOKE.txt");
    private static final Path CHEER_FILE_PATH = DATA_FOLDER_PATH.resolve("cheer.txt");

    public Storage() {
        ensureDataFileExists();
    }

    /**
     * Ensures the data folder and data file exist. Creates them if missing.
     *
     * @throws IOException If folder/file creation fails.
     */

    private void ensureDataFileExists() {
        try {
            Files.createDirectories(DATA_FOLDER_PATH);
            if (Files.notExists(DATA_FILE_PATH)) {
                Files.createFile(DATA_FILE_PATH);
            }
            if (Files.notExists(CHEER_FILE_PATH)) {
                Files.createFile(CHEER_FILE_PATH);
            }
        } catch (IOException e) {
            System.out.println("OOPS!!! I couldn't set up the data file: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the data file.
     *
     * @return A list of tasks loaded from disk.
     * @throws IOException If reading the file fails.
     */

    public TaskList loadTasks() {
        TaskList taskList = new TaskList();

        try {
            List<String> lines = Files.readAllLines(DATA_FILE_PATH);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 3) {
                    continue;
                }

                Task.TaskType type;
                try {
                    type = Task.TaskType.valueOf(parts[0].trim());
                } catch (IllegalArgumentException e) {
                    continue;
                }

                boolean done = parts[1].trim().equals("1");
                String description = parts[2].trim();

                LocalDateTime dateTime = null;
                if (parts.length >= 4 && !parts[3].trim().isEmpty()) {
                    try {
                        dateTime = LocalDateTime.parse(parts[3].trim());
                    } catch (DateTimeParseException e) {
                        // If parsing fails, dateTime remains null
                    }
                }

                Task task = new Task(description, type, done, dateTime);
                taskList.addTask(task);
            }
        } catch (IOException e) {
            System.out.println("OOPS!!! I couldn't load saved data: " + e.getMessage());
        }

        return taskList;
    }

    /**
     * Saves the given tasks to disk.
     *
     * @param taskList Task list to save.
     */

    public void saveTasks(TaskList taskList) {
        try {
            StringBuilder content = new StringBuilder();
            for (Task task : taskList.getAllTasks()) {
                content.append(task.toFileFormat()).append("\n");
            }
            Files.writeString(DATA_FILE_PATH, content.toString());
        } catch (IOException e) {
            System.out.println("OOPS!!! I couldn't save data: " + e.getMessage());
        }
    }

    public String getRandomCheerQuote() {
        try {
            List<String> lines = Files.readAllLines(CHEER_FILE_PATH);
            lines.removeIf(s -> s.trim().isEmpty());

            if (lines.isEmpty()) {
                return "Keep going — you’re doing great!";
            }

            int index = (int) (Math.random() * lines.size());
            return lines.get(index);
        } catch (IOException e) {
            return "Keep going — you’re doing great!";
        }
    }
}
