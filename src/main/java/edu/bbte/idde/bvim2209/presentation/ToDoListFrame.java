package edu.bbte.idde.bvim2209.presentation;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.ToDo;
import edu.bbte.idde.bvim2209.services.ToDoService;
import edu.bbte.idde.bvim2209.services.ToDoServiceImpl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class ToDoListFrame extends JFrame {
    private static ToDoService toDoService;
    private static DefaultTableModel tableModel;

    public ToDoListFrame() {
        toDoService = new ToDoServiceImpl();
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

        JButton insertButton = new JButton("Insert to do");
        JButton updateButton = new JButton("Update to do");
        JButton deleteButton = new JButton("Delete to do");

        inputButtonsPanel.add(insertButton);
        inputButtonsPanel.add(updateButton);
        inputButtonsPanel.add(deleteButton);


        inputPanel.add(writingInputPanel);
        inputPanel.add(inputButtonsPanel);

        JPanel tableOutputPanel = new JPanel();
        tableOutputPanel.setBorder(createCustomTitledBorder("Table Output"));

        String[] columnNames = {"ID", "Title", "Description", "Due date", "Importance level"};
        Object[][] tableData = {};
        tableModel = new DefaultTableModel(tableData, columnNames);
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
        systemOutputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel systemOutputLabel = new JLabel();
        systemOutputPanel.add(systemOutputLabel);

        mainPanel.add(inputPanel);
        mainPanel.add(tableOutputPanel);
        mainPanel.add(systemOutputPanel);

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        refreshTable();

        insertButton.addActionListener(e -> {
            String title = inputTitleTextField.getText();
            String description = inputDescriptionTextField.getText();
            String dueDate = inputDueDateTextField.getText();
            String importanceLevel = inputImportanceLevelTextField.getText();
            try {
                ToDo toDo = new ToDo();
                toDo.setTitle(title);
                toDo.setDescription(description);
                toDo.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(dueDate));
                toDo.setLevelOfImportance(Integer.parseInt(importanceLevel));
                toDoService.createToDo(toDo);
                refreshTable();
                systemOutputLabel.setText("New To Do was added successfully.");
                systemOutputLabel.setForeground(new Color(0, 100, 0));
            } catch (ParseException | IllegalArgumentException ex) {
                systemOutputLabel.setText("Error: " + ex.getMessage());
                systemOutputLabel.setForeground(Color.RED);
            }
            inputTitleTextField.setText("");
            inputDescriptionTextField.setText("");
            inputDueDateTextField.setText("");
            inputImportanceLevelTextField.setText("");
        });

        updateButton.addActionListener(e -> {
            String title = inputTitleTextField.getText();
            String description = inputDescriptionTextField.getText();
            String dueDate = inputDueDateTextField.getText();
            String importanceLevel = inputImportanceLevelTextField.getText();
            String id = JOptionPane.showInputDialog(this, "Enter ID of to do:");
            try {
                ToDo toDo = new ToDo();
                toDo.setTitle(title);
                toDo.setDescription(description);
                toDo.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(dueDate));
                toDo.setLevelOfImportance(Integer.parseInt(importanceLevel));
                toDo.setId(Long.valueOf(id));
                toDoService.updateToDo(toDo);
                refreshTable();
                systemOutputLabel.setText("To Do with id " + id + " updated successfully.");
                systemOutputLabel.setForeground(new Color(0, 100, 0));
            } catch (EntityNotFoundException | ParseException | IllegalArgumentException ex) {
                systemOutputLabel.setText("Error: " + ex.getMessage());
                systemOutputLabel.setForeground(Color.RED);
            }
        });

        deleteButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter ID of to do:");
            try {
                toDoService.deleteToDo(Long.parseLong(id));
                refreshTable();
                systemOutputLabel.setText("To Do with id " + id + " deleted successfully.");
                systemOutputLabel.setForeground(new Color(0, 100, 0));
            } catch (EntityNotFoundException | ParseException ex) {
                systemOutputLabel.setText("Error: " + ex.getMessage());
                systemOutputLabel.setForeground(Color.RED);
            }
        });
    }

    private static void refreshTable() {
        tableModel.setRowCount(0);
        Collection<ToDo> toDoCollection = toDoService.findAll();
        for (ToDo toDo : toDoCollection) {
            Object[] rowData = {
                    toDo.getId(),
                    toDo.getTitle(),
                    toDo.getDescription(),
                    new SimpleDateFormat("yyyy-MM-dd").format(toDo.getDueDate()),
                    toDo.getLevelOfImportance(),
            };
            tableModel.addRow(rowData);
        }
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
