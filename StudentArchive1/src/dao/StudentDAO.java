package dao;

import database.DBConnection;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public boolean insertStudent(Student student) {
        String sql = "INSERT INTO Students " +
                "(StudentID, FullName, MotherName, BirthDate, Nationality, Religion, MaritalStatus, NationalID, PhoneNumber, City, Street, Qualification, Semester, AcademicYear, Major) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setStudentValues(ps, student);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE Students SET " +
                "FullName=?, MotherName=?, BirthDate=?, Nationality=?, Religion=?, MaritalStatus=?, NationalID=?, PhoneNumber=?, City=?, Street=?, Qualification=?, Semester=?, AcademicYear=?, Major=? " +
                "WHERE StudentID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getFullName());
            ps.setString(2, student.getMotherName());
            ps.setString(3, student.getBirthDate());
            ps.setString(4, student.getNationality());
            ps.setString(5, student.getReligion());
            ps.setString(6, student.getMaritalStatus());
            ps.setString(7, student.getNationalId());
            ps.setString(8, student.getPhoneNumber());
            ps.setString(9, student.getCity());
            ps.setString(10, student.getStreet());
            ps.setString(11, student.getQualification());
            ps.setString(12, student.getSemester());
            ps.setString(13, student.getAcademicYear());
            ps.setString(14, student.getMajor());
            ps.setInt(15, student.getStudentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM Students WHERE StudentID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean studentExists(int studentId) {

        String sql = "SELECT StudentID FROM Students WHERE StudentID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public Student searchStudent(int studentId) {
        String sql = "SELECT * FROM Students WHERE StudentID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapStudent(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Students ORDER BY StudentID";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private void setStudentValues(PreparedStatement ps, Student student) throws SQLException {
        ps.setInt(1, student.getStudentId());
        ps.setString(2, student.getFullName());
        ps.setString(3, student.getMotherName());
        ps.setString(4, student.getBirthDate());
        ps.setString(5, student.getNationality());
        ps.setString(6, student.getReligion());
        ps.setString(7, student.getMaritalStatus());
        ps.setString(8, student.getNationalId());
        ps.setString(9, student.getPhoneNumber());
        ps.setString(10, student.getCity());
        ps.setString(11, student.getStreet());
        ps.setString(12, student.getQualification());
        ps.setString(13, student.getSemester());
        ps.setString(14, student.getAcademicYear());
        ps.setString(15, student.getMajor());
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("StudentID"),
                rs.getString("FullName"),
                rs.getString("MotherName"),
                rs.getString("BirthDate"),
                rs.getString("Nationality"),
                rs.getString("Religion"),
                rs.getString("MaritalStatus"),
                rs.getString("NationalID"),
                rs.getString("PhoneNumber"),
                rs.getString("City"),
                rs.getString("Street"),
                rs.getString("Qualification"),
                rs.getString("Semester"),
                rs.getString("AcademicYear"),
                rs.getString("Major")
        );
    }
}