package model;

public class Student {

    private int studentId;
    private String fullName;
    private String motherName;
    private String birthDate;
    private String nationality;
    private String religion;
    private String maritalStatus;
    private String nationalId;
    private String phoneNumber;
    private String city;
    private String street;
    private String qualification;
    private String semester;
    private String academicYear;
    private String major;

    public Student() {
    }

    public Student(int studentId, String fullName, String motherName,
                   String birthDate, String nationality, String religion,
                   String maritalStatus, String nationalId,
                   String phoneNumber, String city, String street,
                   String qualification, String semester,
                   String academicYear, String major) {

        this.studentId = studentId;
        this.fullName = fullName;
        this.motherName = motherName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.religion = religion;
        this.maritalStatus = maritalStatus;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.qualification = qualification;
        this.semester = semester;
        this.academicYear = academicYear;
        this.major = major;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}