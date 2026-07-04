package gui;

import database.DBConnection;
import util.Session;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;

    public LoginForm() {
        setTitle("Login - Student Archive System");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(220, 235, 245));
        add(panel);

        JLabel lblTitle = new JLabel("Student Archive System");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(70, 30, 360, 40);
        panel.add(lblTitle);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUsername.setBounds(90, 100, 120, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(210, 100, 200, 28);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPassword.setBounds(90, 150, 120, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(210, 150, 200, 28);
        panel.add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogin.setBounds(140, 220, 100, 35);
        panel.add(btnLogin);

        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(270, 220, 100, 35);
        panel.add(btnExit);

        btnLogin.addActionListener(e -> login());
        btnExit.addActionListener(e -> System.exit(0));

        getRootPane().setDefaultButton(btnLogin);
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }

        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	String role = rs.getString("Role");
            	new MainForm(role).setVisible(true);                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection error:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}