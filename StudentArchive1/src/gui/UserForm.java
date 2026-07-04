package gui;

import dao.UserDAO;
import model.User;
import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserForm extends JFrame {

    private JTextField txtUserId, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserDAO dao = new UserDAO();

    public UserForm() {
        setTitle("User Management");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(root);

        JLabel title = new JLabel("User Management", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        root.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(Theme.PANEL);
        formPanel.setBorder(BorderFactory.createTitledBorder("User Information"));
        root.add(formPanel, BorderLayout.CENTER);

        txtUserId = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        cmbRole = new JComboBox<>(new String[]{"Admin", "Supervisor", "User"});

        addField(formPanel, "User ID", txtUserId);
        addField(formPanel, "Username", txtUsername);
        addField(formPanel, "Password", txtPassword);
        addField(formPanel, "Role", cmbRole);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Theme.BACKGROUND);
        root.add(bottomPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
                new String[]{"User ID", "Username", "Password", "Role"}, 0
        );

        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(800, 130));
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
        btnSave.addActionListener(e -> saveUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnSearch.addActionListener(e -> searchUser());

        btnBack.addActionListener(e -> {
            new MainForm().setVisible(true);
            dispose();
        });

        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                fillFieldsFromTable();
            }
        });

        loadUsers();
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

    private User getUserFromFields() {
        return new User(
                Integer.parseInt(txtUserId.getText().trim()),
                txtUsername.getText().trim(),
                new String(txtPassword.getPassword()).trim(),
                cmbRole.getSelectedItem().toString()
        );
    }

    private void saveUser() {
    	if (txtUserId.getText().trim().isEmpty() ||
    		    txtUsername.getText().trim().isEmpty() ||
    		    txtPassword.getPassword().length == 0) {

    		    JOptionPane.showMessageDialog(this,
    		            "Please fill all fields.");
    		    return;
    		}

    		if (!txtUserId.getText().matches("\\d+")) {
    		    JOptionPane.showMessageDialog(this,
    		            "User ID must contain numbers only.");
    		    return;
    		}
        try {
            User user = getUserFromFields();

            if (dao.insertUser(user)) {
                JOptionPane.showMessageDialog(this, "User saved successfully");
                clearFields();
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Save failed");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "User ID must be a number");
        }
    }

    private void updateUser() {
        try {
            User user = getUserFromFields();

            if (dao.updateUser(user)) {
                JOptionPane.showMessageDialog(this, "User updated successfully");
                clearFields();
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "User ID must be a number");
        }
    }

    private void deleteUser() {
        try {
            int id = Integer.parseInt(txtUserId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this user?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteUser(id)) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully");
                    clearFields();
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Delete failed");
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid User ID");
        }
    }

    private void searchUser() {
        try {
            int id = Integer.parseInt(txtUserId.getText().trim());
            User user = dao.searchUser(id);

            tableModel.setRowCount(0);

            if (user != null) {
                addUserToTable(user);
                fillFields(user);
            } else {
                JOptionPane.showMessageDialog(this, "User not found");
                loadUsers();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter User ID to search");
        }
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = dao.getAllUsers();

        for (User u : users) {
            addUserToTable(u);
        }
    }

    private void addUserToTable(User u) {
        tableModel.addRow(new Object[]{
                u.getUserId(),
                u.getUsername(),
                u.getPassword(),
                u.getRole()
        });
    }

    private void fillFieldsFromTable() {
        int row = userTable.getSelectedRow();

        txtUserId.setText(tableModel.getValueAt(row, 0).toString());
        txtUsername.setText(tableModel.getValueAt(row, 1).toString());
        txtPassword.setText(tableModel.getValueAt(row, 2).toString());
        cmbRole.setSelectedItem(tableModel.getValueAt(row, 3).toString());
    }

    private void fillFields(User u) {
        txtUserId.setText(String.valueOf(u.getUserId()));
        txtUsername.setText(u.getUsername());
        txtPassword.setText(u.getPassword());
        cmbRole.setSelectedItem(u.getRole());
    }

    private void clearFields() {
        txtUserId.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbRole.setSelectedIndex(0);
        userTable.clearSelection();
    }
}