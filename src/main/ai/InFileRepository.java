package main.ai;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class InFileRepository implements AgentRepository {

    private static final String FILE_PATH = "value_function.txt";

    @Override
    public Map<String, Double> readValueFunction() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            System.out.println("[INFO] No existing value function file found.");
            return new HashMap<>();
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines()
                    .filter(line -> !line.isEmpty() && line.contains(":"))
                    .map(line -> line.split(":", 2))
                    .collect(Collectors.toMap(
                            parts -> parts[0],
                            parts -> Double.parseDouble(parts[1]),
                            (a, b) -> b, // merge function in case of duplicates
                            HashMap::new
                    ));
        } catch (IOException | NumberFormatException e) {
            System.err.println("[ERROR] Failed to read value function: " + e.getMessage());
            return new HashMap<>();
        }
    }

    @Override
    public boolean saveValueFunction(Map<String, Double> valueFunction) {
        Path path = Paths.get(FILE_PATH);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Map.Entry<String, Double> entry : valueFunction.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save value function: " + e.getMessage());
            return false;
        }
    }
}
