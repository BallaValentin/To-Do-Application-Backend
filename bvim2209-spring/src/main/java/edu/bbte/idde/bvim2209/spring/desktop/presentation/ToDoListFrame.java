package edu.bbte.idde.bvim2209.spring.desktop.presentation;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import java.util.Locale;

@Component
public class ToDoListFrame extends JFrame {
    private final ToDoService toDoService;
    private static final JPanel mainPanel = new JPanel();
    private static final JPanel inputPanel = new JPanel();
    private static final JPanel writingInputPanel = new JPanel();
    private static final JLabel inputTitleLabel = new JLabel();
    private static final JTextField inputTitleTextField = new JTextField();
    private static final JLabel inputDescriptionLabel = new JLabel();
    private static final JTextField inputDescriptionTextField = new JTextField();
    private static final JLabel inputDueDateLabel = new JLabel();
    private static final JTextField inputDueDateTextField = new JTextField();
    private static final JLabel inputImportanceLevelLabel = new JLabel();
    private static final JTextField inputImportanceLevelTextField = new JTextField();
    private static final JPanel inputButtonsPanel = new JPanel();
    private static final JButton insertButton = new JButton();
    private static final JButton updateButton = new JButton();
    private static final JButton deleteButton = new JButton();
    private static final JPanel tableOutputPanel = new JPanel();
    private static final JTable table = new JTable();
    private static final JScrollPane scrollPane = new JScrollPane();
    private static final JPanel systemOutputPanel = new JPanel();
    private static final JLabel systemOutputLabel = new JLabel();
    private static final DefaultTableModel tableModel = new DefaultTableModel();

    @Autowired
    public ToDoListFrame(ToDoService toDoService) {
        super();
        this.toDoService = toDoService;

        this.setSize(1280, 720);
        this.setTitle("To Do List application");
        this.setVisible(true);

        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        inputPanel.setLayout(new GridLayout(2, 1, 0, 50));
        inputPanel.setBorder(createCustomTitledBorder("User Input"));

        addInputFields();

        writingInputPanel.setLayout(new GridLayout(2, 4, 5, 5));

        inputButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        insertButton.setText("Insert to do");
        updateButton.setText("Update to do");
        deleteButton.setText("Delete to do");

        inputButtonsPanel.add(insertButton);
        inputButtonsPanel.add(updateButton);
        inputButtonsPanel.add(deleteButton);


        inputPanel.add(writingInputPanel);
        inputPanel.add(inputButtonsPanel);

        tableOutputPanel.setBorder(createCustomTitledBorder("Table Output"));

        String[] columnNames = {"ID", "Title", "Description", "Due date", "Importance level"};
        Object[][] tableData = {};
        tableModel.setDataVector(tableData, columnNames);
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(250);
        }

        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(1260, 200));
        table.setFillsViewportHeight(true);
        tableOutputPanel.add(scrollPane);


        systemOutputPanel.setBorder(createCustomTitledBorder("System Output"));
        systemOutputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        systemOutputPanel.add(systemOutputLabel);

        mainPanel.add(inputPanel);
        mainPanel.add(tableOutputPanel);
        mainPanel.add(systemOutputPanel);

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.pack();
        refreshTable();

        addInsertButtonListener();

        addUpdateButtonListener();

        addDeleteButtonListener();

    }

    private static void addInputFields() {
        inputTitleLabel.setText("Title: ");
        inputTitleTextField.setColumns(30);
        writingInputPanel.add(inputTitleLabel);
        writingInputPanel.add(inputTitleTextField);

        inputDescriptionLabel.setText("Description: ");
        inputDescriptionTextField.setColumns(30);
        writingInputPanel.add(inputDescriptionLabel);
        writingInputPanel.add(inputDescriptionTextField);

        inputDueDateLabel.setText("Due date: ");
        inputDueDateTextField.setColumns(30);
        writingInputPanel.add(inputDueDateLabel);
        writingInputPanel.add(inputDueDateTextField);

        inputImportanceLevelLabel.setText("Importance level: ");
        inputImportanceLevelTextField.setColumns(30);
        writingInputPanel.add(inputImportanceLevelLabel);
        writingInputPanel.add(inputImportanceLevelTextField);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        Collection<ToDo> toDoCollection = toDoService.findAll();
        for (ToDo toDo : toDoCollection) {
            Object[] rowData = {
                    toDo.getId(),
                    toDo.getTitle(),
                    toDo.getDescription(),
                    new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(toDo.getDueDate()),
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

    private void addInsertButtonListener() {
        insertButton.addActionListener(e -> {
            String title = inputTitleTextField.getText();
            String description = inputDescriptionTextField.getText();
            String dueDate = inputDueDateTextField.getText();
            String importanceLevel = inputImportanceLevelTextField.getText();
            try {
                ToDo toDo = new ToDo();
                toDo.setTitle(title);
                toDo.setDescription(description);
                toDo.setDueDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dueDate));
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
    }

    private void addUpdateButtonListener() {
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
                toDo.setDueDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dueDate));
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
    }

    private void addDeleteButtonListener() {
        deleteButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter ID of to do:");
            try {
                toDoService.deleteToDo(Long.parseLong(id));
                refreshTable();
                systemOutputLabel.setText("To Do with id " + id + " deleted successfully.");
                systemOutputLabel.setForeground(new Color(0, 100, 0));
            } catch (ParseException | EntityNotFoundException ex) {
                systemOutputLabel.setText("Error: " + ex.getMessage());
                systemOutputLabel.setForeground(Color.RED);
            }
        });
    }
}
