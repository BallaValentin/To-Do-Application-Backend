package edu.bbte.idde.bvim2209.presentation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ToDoListFrame extends JFrame {
    public ToDoListFrame() {
        this.setSize(1280, 720);
        this.setTitle("To Do List application");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1, 0, 50));
        inputPanel.setBorder(createCustomTitledBorder("User Input"));

        JPanel writingInputPanel = new JPanel();
        writingInputPanel.setLayout(new GridLayout(2, 4, 5, 5));

        JLabel inputTitleLabel = new JLabel();
        inputTitleLabel.setText("Title: ");
        JTextField inputTitleTextField = new JTextField(30);
        writingInputPanel.add(inputTitleLabel);
        writingInputPanel.add(inputTitleTextField);

        JLabel inputDescriptionLabel = new JLabel();
        inputDescriptionLabel.setText("Description: ");
        JTextField inputDescriptionTextField = new JTextField(30);
        writingInputPanel.add(inputDescriptionLabel);
        writingInputPanel.add(inputDescriptionTextField);

        JLabel inputDueDateLabel = new JLabel();
        inputDueDateLabel.setText("Due date: ");
        JTextField inputDueDateTextField = new JTextField(30);
        writingInputPanel.add(inputDueDateLabel);
        writingInputPanel.add(inputDueDateTextField);

        JLabel inputImportanceLevelLabel = new JLabel();
        inputImportanceLevelLabel.setText("Importance level: ");
        JTextField inputImportanceLevelTextField = new JTextField(30);
        writingInputPanel.add(inputImportanceLevelLabel);
        writingInputPanel.add(inputImportanceLevelTextField);

        JPanel inputButtonsPanel = new JPanel();
        inputButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Add to do");
        JButton updateButton = new JButton("Update to do");
        JButton deleteButton = new JButton("Delete to do");

        inputButtonsPanel.add(addButton);
        inputButtonsPanel.add(updateButton);
        inputButtonsPanel.add(deleteButton);

        inputPanel.add(writingInputPanel);
        inputPanel.add(inputButtonsPanel);

        JPanel tableOutputPanel = new JPanel();
        tableOutputPanel.setBorder(createCustomTitledBorder("Table Output"));

        String[] columnNames = {"ID", "Title", "Description", "Due date", "Importance level"};
        Object[][] tableData = {};
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(250);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1260, 200));
        table.setFillsViewportHeight(true);
        tableOutputPanel.add(scrollPane);


        JPanel systemOutputPanel = new JPanel();
        systemOutputPanel.setBorder(createCustomTitledBorder("System Output"));

        mainPanel.add(inputPanel);
        mainPanel.add(tableOutputPanel);
        mainPanel.add(systemOutputPanel);

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private static Border createCustomTitledBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title);
        Border marginBorder = new EmptyBorder(10, 10, 10, 10);
        return BorderFactory.createCompoundBorder(
                marginBorder,
                titledBorder);
    }

    public static void main(String[] args) {
        new ToDoListFrame();
    }
}
