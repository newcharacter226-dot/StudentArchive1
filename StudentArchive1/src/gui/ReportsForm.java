package gui;

import database.DBConnection;
import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReportsForm extends JFrame {

    public ReportsForm() {
        setTitle("Reports & Inquiry");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(root);

        JLabel title = new JLabel("Reports & Inquiry", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        root.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(Theme.BACKGROUND);
        root.add(panel, BorderLayout.CENTER);

        JButton btnStudents = createBigButton("Students Report");
        JButton btnDocuments = createBigButton("Documents Report");
        JButton btnUsers = createBigButton("Users Report");
        JButton btnSearch = createBigButton("Search Student");

        panel.add(btnStudents);
        panel.add(btnDocuments);
        panel.add(btnUsers);
        panel.add(btnSearch);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(Theme.BUTTON_FONT);

        JPanel bottom = new JPanel();
        bottom.setBackground(Theme.BACKGROUND);
        bottom.add(btnBack);
        root.add(bottom, BorderLayout.SOUTH);

        btnStudents.addActionListener(e ->
                showReport("Students Report", "SELECT * FROM Students ORDER BY StudentID"));

        btnDocuments.addActionListener(e ->
                showReport("Documents Report", "SELECT * FROM Documents ORDER BY DocumentID"));

        btnUsers.addActionListener(e ->
                showReport("Users Report", "SELECT UserID, Username, Role FROM Users ORDER BY UserID"));

        btnSearch.addActionListener(e -> searchStudent());

        btnBack.addActionListener(e -> {
            new MainForm().setVisible(true);
            dispose();
        });
    }

    private JButton createBigButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.BUTTON_FONT);
        return button;
    }

    private void showReport(String title, String sql) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            DefaultTableModel model = buildTableModel(rs);

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No data found");
                return;
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame(title);
            frame.setSize(900, 500);
            frame.setLocationRelativeTo(null);
            frame.add(scrollPane);
            frame.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Report failed:\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void searchStudent() {
        String keyword = JOptionPane.showInputDialog(this, "Enter Student ID or Full Name:");

        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM Students WHERE StudentID = ? OR FullName LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int studentId = -1;

            try {
                studentId = Integer.parseInt(keyword.trim());
            } catch (NumberFormatException ignored) {
            }

            ps.setInt(1, studentId);
            ps.setString(2, "%" + keyword.trim() + "%");

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = buildTableModel(rs);

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No student found");
                return;
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Search Student Result");
            frame.setSize(900, 500);
            frame.setLocationRelativeTo(null);
            frame.add(scrollPane);
            frame.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Search failed:\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        DefaultTableModel model = new DefaultTableModel();

        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(metaData.getColumnName(i));
        }

        while (rs.next()) {
            Object[] row = new Object[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }

            model.addRow(row);
        }

        return model;
    }
}