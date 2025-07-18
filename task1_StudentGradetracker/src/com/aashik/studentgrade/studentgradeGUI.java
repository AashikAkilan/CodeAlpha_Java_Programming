package com.aashik.studentgrade;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.io.*;
import java.util.ArrayList; 

public class studentgradeGUI {
    private static ArrayList<student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        loadFromFile();

        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JTextField nameField = new JTextField(15);
        JTextField gradeField = new JTextField(5);
        JButton addButton = new JButton("Add Student");
        JButton reportButton = new JButton("Show Report");
        JButton exitButton = new JButton("Exit");
        JTextArea textArea = new JTextArea(15, 30);
        textArea.setEditable(false);

        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Grade:"));
        frame.add(gradeField);
        frame.add(addButton);
        frame.add(reportButton);
        frame.add(exitButton);
        frame.add(new JScrollPane(textArea));

        // Add Student button
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String gradeText = gradeField.getText().trim();
            if (name.isEmpty() || gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both name and grade.");
                return;
            }
            try {
                double grade = Double.parseDouble(gradeText);
                students.add(new student(name, grade));
                saveToFile();
                JOptionPane.showMessageDialog(frame, "Student added successfully!");
                nameField.setText("");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Grade must be a number.");
            }
        });

        // Report button
        reportButton.addActionListener(e -> {
            if (students.isEmpty()) {
                textArea.setText("No students to display.");
                return;
            }

            double total = 0;
            double highest = Double.MIN_VALUE;
            double lowest = Double.MAX_VALUE;

            StringBuilder sb = new StringBuilder();
            for (student s : students) {
                double grade = s.getGrade();
                total += grade;
                if (grade > highest) highest = grade;
                if (grade < lowest) lowest = grade;
                sb.append("Name: ").append(s.getName()).append(", Grade: ").append(grade).append("\n");
            }

            double average = total / students.size();

            sb.append("\nAverage grade: ").append(average);
            sb.append("\nHighest grade: ").append(highest);
            sb.append("\nLowest grade: ").append(lowest);

            textArea.setText(sb.toString());
        });

        // Exit button
        exitButton.addActionListener(e -> {
            frame.dispose();
        });

        frame.setVisible(true);
    }

    private static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (student s : students) {
                writer.write(s.getName() + "," + s.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        students.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    double grade = Double.parseDouble(parts[1]);
                    students.add(new student(name, grade));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
