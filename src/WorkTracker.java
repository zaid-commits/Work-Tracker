package src;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class WorkTracker {

    private static final String FILENAME = "emotions.txt";

    public static void main(String[] args) {
        // Check if the file exists
        File file = new File(FILENAME);
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            // If the file doesn't exist, create a new one and write a header
            if (!fileExists) {
                writer.write("Emotion Log\n");
            }

            // Input message
            String message = JOptionPane.showInputDialog("Enter your current emotion/feeling:");

            // Save message with current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            writer.write(formattedDateTime + ": " + message + "\n");

            JOptionPane.showMessageDialog(null, "Emotion saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving emotion: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error saving emotion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
