package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WorkTracker {
    private static final String MESSAGE_FILENAME = "messages.txt";
    private static final String SUMMARY_FILENAME = "summaries.txt";

    private static JTextArea messagesArea;

    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame("Work Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Add a label with padding
        JLabel label = new JLabel("What are you thinking now?");
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.add(label, BorderLayout.NORTH);

        // Add buttons for adding messages and summaries
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addMessageButton = new JButton("Add Message");
        addMessageButton.addActionListener(e -> addMessage());
        buttonPanel.add(addMessageButton);

        JButton addSummaryButton = new JButton("Add Summary");
        addSummaryButton.setEnabled(LocalTime.now().isAfter(LocalTime.of(21, 0)));
        addSummaryButton.addActionListener(e -> addSummary());
        buttonPanel.add(addSummaryButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add a text area for displaying messages and summaries
        messagesArea = new JTextArea();
        messagesArea.setEditable(false);
        JScrollPane messagesScrollPane = new JScrollPane(messagesArea);
        frame.add(messagesScrollPane, BorderLayout.CENTER);

        // Load and display existing messages and summaries
        loadMessages();
        loadSummaries();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static void addMessage() {
        String message = JOptionPane.showInputDialog(null, "Enter what you are thinking now");
        if (message != null && !message.isEmpty()) {
            saveMessage(MESSAGE_FILENAME, message);
            messagesArea.append("Message: " + message + "\n");
        }
    }

    private static void addSummary() {
        String summary = JOptionPane.showInputDialog(null, "Enter a summary of the day");
        if (summary != null && !summary.isEmpty()) {
            saveSummary(SUMMARY_FILENAME, summary);
            messagesArea.append("Summary: " + summary + "\n\n");
        }
    }

    private static void saveMessage(String filename, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            File file = new File(filename);
            boolean fileExists = file.exists();
            if (!fileExists) {
                writer.write("Message Log\n");
            }
            writer.write(message + "\n");
        } catch (IOException ex) {
            System.err.println("Error saving Message: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error saving message: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void saveSummary(String filename, String summary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            File file = new File(filename);
            boolean fileExists = file.exists();
            if (!fileExists) {
                writer.write("Summary Log\n");
            }
            writer.write(summary + "\n\n");
        } catch (IOException ex) {
            System.err.println("Error saving Summary: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error saving summary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void loadMessages() {
        List<String> messages = readFile(MESSAGE_FILENAME);
        for (String message : messages) {
            messagesArea.append("Message: " + message + "\n");
        }
    }

    private static void loadSummaries() {
        List<String> summaries = readFile(SUMMARY_FILENAME);
        for (String summary : summaries) {
            messagesArea.append("Summary: " + summary + "\n\n");
        }
    }

    private static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            System.err.println("Error reading file: " + ex.getMessage());
        }
        return lines;
    }
}