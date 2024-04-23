package src;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkTracker {
    private static final String MESSAGE_FILE = "messages.txt";
    private static final String SUMMARY_FILE = "summary.txt";

    private static JTextArea messagesArea;
    private static JButton addButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Work Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        messagesArea = new JTextArea();
        messagesArea.setEditable(false);
        panel.add(new JScrollPane(messagesArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Summary");
        addButton.addActionListener(e -> addSummary());
        buttonPanel.add(addButton);

        JButton addMessageButton = new JButton("Add Message");
        addMessageButton.addActionListener(e -> addMessage());
        buttonPanel.add(addMessageButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearMessages());
        buttonPanel.add(clearButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        updateMessages();

        Timer timer = new Timer(60000, e -> updateMessages());
        timer.start();

    }

    private static void updateMessages() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
    
        if (hour >= 12 || hour < 5) {
            addButton.setEnabled(true);
            addButton.setText("Add Summary");
        } else {
            addButton.setEnabled(false);
            addButton.setText("Summary update enabled after 9 PM until 12 AM.");
        }
    
        messagesArea.setText("Current Date and Time: " + getFormattedDateTime() + "\n");
        messagesArea.append("--------------------------------------------------\n");
        messagesArea.append("Messages:\n");
    
        // Load messages from file and display them
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MESSAGE_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                messagesArea.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        messagesArea.append("\n--------------------------------------------------\n");
        messagesArea.append("Summaries:\n");
    
        // Load summaries from file and display them
        try {
            BufferedReader reader = new BufferedReader(new FileReader(SUMMARY_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                messagesArea.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void addSummary() {
        String summary = JOptionPane.showInputDialog(null, "What intresting happened today?",
                "Zaid Loves listening about your day !",
                JOptionPane.PLAIN_MESSAGE);
        if (summary != null && !summary.isEmpty()) {
            saveSummary(summary);
            updateMessages();
        }
    }

    private static void saveSummary(String summary) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(SUMMARY_FILE, true));
            writer.write(getFormattedDateTime() + " - " + summary + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFormattedDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    private static void addMessage() {
        // String message = JOptionPane.showInputDialog(null, "Enter a message:", "Add
        // Message",
        String message = JOptionPane.showInputDialog(null, "Tell zaid what happened",
                "Zaid Loves Listening to you :)",

                JOptionPane.PLAIN_MESSAGE);
        if (message != null && !message.isEmpty()) {
            saveMessage(message);
            updateMessages();
        }
    }

    private static void clearMessages() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(MESSAGE_FILE));
            writer.write("");
            writer.close();
            updateMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveMessage(String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = formatter.format(new Date());

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(MESSAGE_FILE, true));
            writer.write(formattedTime + " - " + message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}