package gui;

import database.DBConnection;
import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class DocumentForm extends JFrame {

    private JTextField txtDocumentId, txtStudentId, txtDocumentName, txtDocumentDate, txtFilePath;
    private JTable documentTable;
    private DefaultTableModel tableModel;

    public DocumentForm() {
        setTitle("Add Document");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(root);

        JLabel title = new JLabel("Document Management", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        root.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 3, 10, 10));
        formPanel.setBackground(Theme.PANEL);
        formPanel.setBorder(BorderFactory.createTitledBorder("Document Information"));
        root.add(formPanel, BorderLayout.CENTER);

        txtDocumentId = new JTextField();
        txtStudentId = new JTextField();
        txtDocumentName = new JTextField();
        txtDocumentDate = new JTextField();
        txtFilePath = new JTextField();
        txtFilePath.setEditable(false);

        addField(formPanel, "Document ID", txtDocumentId);
        addField(formPanel, "Student ID", txtStudentId);
        addField(formPanel, "Document Name", txtDocumentName);
        addField(formPanel, "Document Date", txtDocumentDate);
        addField(formPanel, "File Path", txtFilePath);

        JButton btnBrowse = createButton("Browse");
        formPanel.add(new JLabel(""));
        formPanel.add(btnBrowse);
        formPanel.add(new JLabel(""));

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Theme.BACKGROUND);
        root.add(bottomPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
                new String[]{"Document ID", "Student ID", "Document Name", "Date", "File Path"}, 0
        );

        documentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(documentTable);
        scrollPane.setPreferredSize(new Dimension(850, 150));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Theme.BACKGROUND);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton btnNew = createButton("New");
        JButton btnSave = createButton("Save");
        JButton btnUpdate = createButton("Update");
        JButton btnDelete = createButton("Delete");
        JButton btnSearch = createButton("Search");
        JButton btnClear = createButton("Clear");
        JButton btnBack = createButton("Back");

        buttonPanel.add(btnNew);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnBack);

        btnBrowse.addActionListener(e -> chooseFile());
        btnNew.addActionListener(e -> clearFields());
        btnClear.addActionListener(e -> clearFields());
        btnSave.addActionListener(e -> saveDocument());
        btnUpdate.addActionListener(e -> updateDocument());
        btnDelete.addActionListener(e -> deleteDocument());
        btnSearch.addActionListener(e -> searchDocument());

        btnBack.addActionListener(e -> {
            new MainForm().setVisible(true);
            dispose();
        });

        documentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && documentTable.getSelectedRow() != -1) {
                fillFieldsFromTable();
            }
        });

        loadDocuments();
    }

    private void addField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.LABEL_FONT);
        panel.add(label);
        panel.add(field);
        panel.add(new JLabel(""));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.BUTTON_FONT);
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtFilePath.setText(selectedFile.getAbsolutePath());
        }
    }

    private void saveDocument() {
    	if (txtStudentId.getText().trim().isEmpty() ||
    		    txtDocumentName.getText().trim().isEmpty() ||
    		    txtDocumentDate.getText().trim().isEmpty() ||
    		    txtFilePath.getText().trim().isEmpty()) {

    		    JOptionPane.showMessageDialog(this,
    		            "Please fill all fields.");
    		    return;
    		}

    		if (!txtStudentId.getText().matches("\\d+")) {
    		    JOptionPane.showMessageDialog(this,
    		            "Student ID must contain numbers only.");
    		    return;
    		}
        String sql = "INSERT INTO Documents (StudentID, DocumentName, DocumentDate, FilePath) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtStudentId.getText().trim()));
            ps.setString(2, txtDocumentName.getText().trim());
            ps.setString(3, txtDocumentDate.getText().trim());
            ps.setString(4, txtFilePath.getText().trim());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Document saved successfully");
            clearFields();
            loadDocuments();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Save failed:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateDocument() {
        String sql = "UPDATE Documents SET StudentID=?, DocumentName=?, DocumentDate=?, FilePath=? WHERE DocumentID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtStudentId.getText().trim()));
            ps.setString(2, txtDocumentName.getText().trim());
            ps.setString(3, txtDocumentDate.getText().trim());
            ps.setString(4, txtFilePath.getText().trim());
            ps.setInt(5, Integer.parseInt(txtDocumentId.getText().trim()));

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Document updated successfully");
                clearFields();
                loadDocuments();
            } else {
                JOptionPane.showMessageDialog(this, "Document not found");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update failed:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteDocument() {
        String sql = "DELETE FROM Documents WHERE DocumentID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int confirm = JOptionPane.showConfirmDialog(this, "Delete this document?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                ps.setInt(1, Integer.parseInt(txtDocumentId.getText().trim()));

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Document deleted successfully");
                    clearFields();
                    loadDocuments();
                } else {
                    JOptionPane.showMessageDialog(this, "Document not found");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete failed:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void searchDocument() {
        String sql = "SELECT * FROM Documents WHERE DocumentID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtDocumentId.getText().trim()));
            ResultSet rs = ps.executeQuery();

            tableModel.setRowCount(0);

            if (rs.next()) {
                addRow(rs);
                fillFieldsFromResult(rs);
            } else {
                JOptionPane.showMessageDialog(this, "Document not found");
                loadDocuments();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Search failed:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadDocuments() {
        tableModel.setRowCount(0);

        String sql = "SELECT * FROM Documents ORDER BY DocumentID";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                addRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRow(ResultSet rs) throws SQLException {
        tableModel.addRow(new Object[]{
                rs.getInt("DocumentID"),
                rs.getInt("StudentID"),
                rs.getString("DocumentName"),
                rs.getString("DocumentDate"),
                rs.getString("FilePath")
        });
    }

    private void fillFieldsFromResult(ResultSet rs) throws SQLException {
        txtDocumentId.setText(String.valueOf(rs.getInt("DocumentID")));
        txtStudentId.setText(String.valueOf(rs.getInt("StudentID")));
        txtDocumentName.setText(rs.getString("DocumentName"));
        txtDocumentDate.setText(rs.getString("DocumentDate"));
        txtFilePath.setText(rs.getString("FilePath"));
    }

    private void fillFieldsFromTable() {
        int row = documentTable.getSelectedRow();

        txtDocumentId.setText(tableModel.getValueAt(row, 0).toString());
        txtStudentId.setText(tableModel.getValueAt(row, 1).toString());
        txtDocumentName.setText(tableModel.getValueAt(row, 2).toString());
        txtDocumentDate.setText(tableModel.getValueAt(row, 3).toString());
        txtFilePath.setText(tableModel.getValueAt(row, 4).toString());
    }

    private void clearFields() {
        txtDocumentId.setText("");
        txtStudentId.setText("");
        txtDocumentName.setText("");
        txtDocumentDate.setText("");
        txtFilePath.setText("");
        documentTable.clearSelection();
    }
}