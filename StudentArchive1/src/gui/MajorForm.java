package gui;

import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MajorForm extends JFrame {

    private JTextField txtMajorId, txtMajorName;
    private JTable majorTable;
    private DefaultTableModel tableModel;

    public MajorForm() {
        setTitle("Major Management");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(root);

        JLabel title = new JLabel("Major Management", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        root.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(Theme.PANEL);
        formPanel.setBorder(BorderFactory.createTitledBorder("Major Information"));
        root.add(formPanel, BorderLayout.CENTER);

        txtMajorId = new JTextField();
        txtMajorName = new JTextField();

        addField(formPanel, "Major ID", txtMajorId);
        addField(formPanel, "Major Name", txtMajorName);

        tableModel = new DefaultTableModel(new String[]{"Major ID", "Major Name"}, 0);
        majorTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(majorTable);
        scrollPane.setPreferredSize(new Dimension(650, 130));
        root.add(scrollPane, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Theme.BACKGROUND);

        JButton btnSave = createButton("Save");
        JButton btnClear = createButton("Clear");
        JButton btnBack = createButton("Back");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnBack);

        root.add(buttonPanel, BorderLayout.EAST);

        btnSave.addActionListener(e -> {
            tableModel.addRow(new Object[]{txtMajorId.getText(), txtMajorName.getText()});
        });

        btnClear.addActionListener(e -> {
            txtMajorId.setText("");
            txtMajorName.setText("");
        });

        btnBack.addActionListener(e -> {
            new MainForm().setVisible(true);
            dispose();
        });
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
        return button;
    }
}