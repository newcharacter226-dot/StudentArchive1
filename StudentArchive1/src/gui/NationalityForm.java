package gui;

import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NationalityForm extends JFrame {

    private JTextField txtNationalityId, txtNationalityName;
    private JTable nationalityTable;
    private DefaultTableModel tableModel;

    public NationalityForm() {
        setTitle("Nationality Management");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(root);

        JLabel title = new JLabel("Nationality Management", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        root.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(Theme.PANEL);
        formPanel.setBorder(BorderFactory.createTitledBorder("Nationality Information"));
        root.add(formPanel, BorderLayout.CENTER);

        txtNationalityId = new JTextField();
        txtNationalityName = new JTextField();

        addField(formPanel, "Nationality ID", txtNationalityId);
        addField(formPanel, "Nationality Name", txtNationalityName);

        tableModel = new DefaultTableModel(new String[]{"Nationality ID", "Nationality Name"}, 0);
        nationalityTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(nationalityTable);
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
            tableModel.addRow(new Object[]{
                    txtNationalityId.getText(),
                    txtNationalityName.getText()
            });
        });

        btnClear.addActionListener(e -> {
            txtNationalityId.setText("");
            txtNationalityName.setText("");
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