package system.BancodeDados;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Local_Id {
    private static final String ID_FILE_PATH = "idfile.txt";

    public int getNextLocalId() {
        int id = 1;
        Path path = Paths.get(ID_FILE_PATH);

        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
                Files.write(path, String.valueOf(id).getBytes());
            } else {
                // Use try-with-resources to auto-close the BufferedReader
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    String content = reader.readLine();
                    id = Integer.parseInt(content.trim());
                    id++;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            // Save the new ID to the file, even if an exception occurs
            try {
                Files.write(path, String.valueOf(id).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return id;
    }
}
