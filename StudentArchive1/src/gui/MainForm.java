package gui;

import util.Theme;
import util.Session;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    private String role;

    public MainForm() {
        this(Session.currentRole);
    }

    public MainForm(String role) {

        this.role = role;
        Session.currentRole = role;

        setTitle("Student Archive System");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setContentPane(root);

        JLabel title = new JLabel("Student Archive System - " + role, SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 28));
        root.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 25, 0));
        center.setBackground(Theme.BACKGROUND);
        root.add(center, BorderLayout.CENTER);

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Theme.PANEL);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        center.add(leftPanel);

        try {

            ImageIcon icon = new ImageIcon(getClass().getResource("/Image/logo.png"));

            Image image = icon.getImage().getScaledInstance(
                    220,
                    220,
                    Image.SCALE_SMOOTH);

            JLabel lblLogo = new JLabel(new ImageIcon(image));
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

            leftPanel.add(lblLogo, BorderLayout.CENTER);

        } catch (Exception e) {

            JLabel lblLogo = new JLabel("UNIVERSITY LOGO", SwingConstants.CENTER);
            lblLogo.setFont(new Font("Tahoma", Font.BOLD, 24));
            leftPanel.add(lblLogo, BorderLayout.CENTER);

        }

        JLabel subtitle = new JLabel("Student Document Archiving", SwingConstants.CENTER);
        subtitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        leftPanel.add(subtitle, BorderLayout.SOUTH);

        // Right Panel
        JPanel rightPanel = new JPanel(new GridLayout(5, 1, 10, 15));
        rightPanel.setBackground(Theme.PANEL);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 45, 40, 45));
        center.add(rightPanel);

        JButton btnStudents = createMenuButton("Student Records");
        JButton btnDocuments = createMenuButton("Add Document");
        JButton btnUsers = createMenuButton("User Management");
        JButton btnReports = createMenuButton("Reports & Inquiry");
        JButton btnExit = createMenuButton("Exit");

        if (role.equalsIgnoreCase("Admin")) {

            rightPanel.add(btnStudents);
            rightPanel.add(btnDocuments);
            rightPanel.add(btnUsers);
            rightPanel.add(btnReports);

        } else if (role.equalsIgnoreCase("Supervisor")) {

            rightPanel.add(btnStudents);
            rightPanel.add(btnDocuments);
            rightPanel.add(btnReports);

        } else {

            rightPanel.add(btnReports);

        }

        rightPanel.add(btnExit);

        btnStudents.addActionListener(e -> openForm(new StudentForm()));
        btnDocuments.addActionListener(e -> openForm(new DocumentForm()));
        btnUsers.addActionListener(e -> openForm(new UserForm()));
        btnReports.addActionListener(e -> openForm(new ReportsForm()));

        btnExit.addActionListener(e -> System.exit(0));
    }

    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);
        button.setFont(Theme.BUTTON_FONT);
        button.setFocusPainted(false);

        return button;
    }

    private void openForm(JFrame form) {

        form.setVisible(true);
        dispose();

    }
}