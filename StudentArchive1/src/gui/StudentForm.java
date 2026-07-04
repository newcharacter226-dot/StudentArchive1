package gui;

import dao.StudentDAO;
import model.Student;
import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentForm extends JFrame {

    private JTextField txtStudentId, txtFullName, txtMotherName, txtBirthDate;
    private JTextField txtNationalId, txtPhone, txtCity, txtStreet;
    private JComboBox<String> cmbNationality, cmbReligion, cmbMaritalStatus;
    private JComboBox<String> cmbQualification, cmbSemester, cmbYear, cmbMajor;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentDAO dao = new StudentDAO();

    public StudentForm() {
        setTitle("Student Records");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(root);

        JLabel title = new JLabel("Student Records", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        root.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(8, 4, 10, 10));
        formPanel.setBackground(Theme.PANEL);
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        root.add(formPanel, BorderLayout.CENTER);

        txtStudentId = new JTextField();
        txtFullName = new JTextField();
        txtMotherName = new JTextField();
        txtBirthDate = new JTextField();
        txtNationalId = new JTextField();
        txtPhone = new JTextField();
        txtCity = new JTextField();
        txtStreet = new JTextField();

        cmbNationality = new JComboBox<>(new String[]{"Libyan", "Egyptian", "Tunisian", "Other"});
        cmbReligion = new JComboBox<>(new String[]{"Muslim", "Christian", "Other"});
        cmbMaritalStatus = new JComboBox<>(new String[]{"Single", "Married"});
        cmbQualification = new JComboBox<>(new String[]{"Secondary", "Diploma", "Bachelor"});
        cmbSemester = new JComboBox<>(new String[]{"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth"});
        cmbYear = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        cmbMajor = new JComboBox<>(new String[]{"Computer Science", "Information Technology", "Clinical Nutrition", "Other"});

        addField(formPanel, "Student ID", txtStudentId);
        addField(formPanel, "Full Name", txtFullName);
        addField(formPanel, "Mother Name", txtMotherName);
        addField(formPanel, "Birth Date", txtBirthDate);
        addField(formPanel, "Nationality", cmbNationality);
        addField(formPanel, "Religion", cmbReligion);
        addField(formPanel, "Marital Status", cmbMaritalStatus);
        addField(formPanel, "National ID", txtNationalId);
        addField(formPanel, "Phone Number", txtPhone);
        addField(formPanel, "City", txtCity);
        addField(formPanel, "Street", txtStreet);
        addField(formPanel, "Qualification", cmbQualification);
        addField(formPanel, "Semester", cmbSemester);
        addField(formPanel, "Academic Year", cmbYear);
        addField(formPanel, "Major", cmbMajor);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Theme.BACKGROUND);
        root.add(bottomPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Full Name", "Mother Name", "Birth Date", "Nationality", "Phone", "Major"}, 0
        );

        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(950, 160));
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

        btnNew.addActionListener(e -> clearFields());
        btnClear.addActionListener(e -> clearFields());
        btnSave.addActionListener(e -> saveStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnSearch.addActionListener(e -> searchStudent());

        btnBack.addActionListener(e -> {
            new MainForm().setVisible(true);
            dispose();
        });

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                fillFieldsFromSelectedRow();
            }
        });

        loadStudents();
    }

    private void addField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.LABEL_FONT);
        panel.add(label);
        panel.add(field);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.BUTTON_FONT);
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private Student getStudentFromFields() {
        return new Student(
                Integer.parseInt(txtStudentId.getText().trim()),
                txtFullName.getText().trim(),
                txtMotherName.getText().trim(),
                txtBirthDate.getText().trim(),
                cmbNationality.getSelectedItem().toString(),
                cmbReligion.getSelectedItem().toString(),
                cmbMaritalStatus.getSelectedItem().toString(),
                txtNationalId.getText().trim(),
                txtPhone.getText().trim(),
                txtCity.getText().trim(),
                txtStreet.getText().trim(),
                cmbQualification.getSelectedItem().toString(),
                cmbSemester.getSelectedItem().toString(),
                cmbYear.getSelectedItem().toString(),
                cmbMajor.getSelectedItem().toString()
        );
    }

    private void saveStudent() {
    	if (txtStudentId.getText().trim().isEmpty() ||
    		    txtFullName.getText().trim().isEmpty() ||
    		    txtMotherName.getText().trim().isEmpty() ||
    		    txtBirthDate.getText().trim().isEmpty() ||
    		    txtNationalId.getText().trim().isEmpty() ||
    		    txtPhone.getText().trim().isEmpty() ||
    		    txtCity.getText().trim().isEmpty() ||
    		    txtStreet.getText().trim().isEmpty()) {

    		    JOptionPane.showMessageDialog(this,
    		            "Please fill all fields.",
    		            "Warning",
    		            JOptionPane.WARNING_MESSAGE);
    		    return;
    		}if (!txtStudentId.getText().matches("\\d+")) {
    		    JOptionPane.showMessageDialog(this,
    		            "Student ID must contain numbers only.");
    		    return;
    		}

    		if (!txtPhone.getText().matches("\\d+")) {
    		    JOptionPane.showMessageDialog(this,
    		            "Phone Number must contain numbers only.");
    		    return;
    		}

    		if (!txtNationalId.getText().matches("\\d+")) {
    		    JOptionPane.showMessageDialog(this,
    		            "National ID must contain numbers only.");
    		    return;
    		}
        try {
            Student student = getStudentFromFields();

            if (dao.insertStudent(student)) {
                JOptionPane.showMessageDialog(this, "Student saved successfully");
                clearFields();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Save failed");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Student ID must be a number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateStudent() {
        try {
            Student student = getStudentFromFields();

            if (dao.updateStudent(student)) {
                JOptionPane.showMessageDialog(this, "Student updated successfully");
                clearFields();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Student ID must be a number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(txtStudentId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteStudent(id)) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully");
                    clearFields();
                    loadStudents();
                } else {
                    JOptionPane.showMessageDialog(this, "Delete failed");
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid Student ID");
        }
    }

    private void searchStudent() {
        try {
            int id = Integer.parseInt(txtStudentId.getText().trim());
            Student student = dao.searchStudent(id);

            tableModel.setRowCount(0);

            if (student != null) {
                addStudentToTable(student);
                fillFields(student);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found");
                loadStudents();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter Student ID to search");
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = dao.getAllStudents();

        for (Student s : students) {
            addStudentToTable(s);
        }
    }

    private void addStudentToTable(Student s) {
        tableModel.addRow(new Object[]{
                s.getStudentId(),
                s.getFullName(),
                s.getMotherName(),
                s.getBirthDate(),
                s.getNationality(),
                s.getPhoneNumber(),
                s.getMajor()
        });
    }

    private void fillFieldsFromSelectedRow() {
        int row = studentTable.getSelectedRow();

        txtStudentId.setText(tableModel.getValueAt(row, 0).toString());

        Student student = dao.searchStudent(Integer.parseInt(txtStudentId.getText()));

        if (student != null) {
            fillFields(student);
        }
    }

    private void fillFields(Student s) {
        txtStudentId.setText(String.valueOf(s.getStudentId()));
        txtFullName.setText(s.getFullName());
        txtMotherName.setText(s.getMotherName());
        txtBirthDate.setText(s.getBirthDate());
        cmbNationality.setSelectedItem(s.getNationality());
        cmbReligion.setSelectedItem(s.getReligion());
        cmbMaritalStatus.setSelectedItem(s.getMaritalStatus());
        txtNationalId.setText(s.getNationalId());
        txtPhone.setText(s.getPhoneNumber());
        txtCity.setText(s.getCity());
        txtStreet.setText(s.getStreet());
        cmbQualification.setSelectedItem(s.getQualification());
        cmbSemester.setSelectedItem(s.getSemester());
        cmbYear.setSelectedItem(s.getAcademicYear());
        cmbMajor.setSelectedItem(s.getMajor());
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtFullName.setText("");
        txtMotherName.setText("");
        txtBirthDate.setText("");
        txtNationalId.setText("");
        txtPhone.setText("");
        txtCity.setText("");
        txtStreet.setText("");
        cmbNationality.setSelectedIndex(0);
        cmbReligion.setSelectedIndex(0);
        cmbMaritalStatus.setSelectedIndex(0);
        cmbQualification.setSelectedIndex(0);
        cmbSemester.setSelectedIndex(0);
        cmbYear.setSelectedIndex(0);
        cmbMajor.setSelectedIndex(0);
        studentTable.clearSelection();
    }
}