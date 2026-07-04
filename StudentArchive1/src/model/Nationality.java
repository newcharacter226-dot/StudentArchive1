package model;

public class Nationality {

    private int nationalityId;
    private String nationalityName;

    public Nationality() {
    }

    public Nationality(int nationalityId, String nationalityName) {
        this.nationalityId = nationalityId;
        this.nationalityName = nationalityName;
    }

    public int getNationalityId() { return nationalityId; }
    public void setNationalityId(int nationalityId) { this.nationalityId = nationalityId; }

    public String getNationalityName() { return nationalityName; }
    public void setNationalityName(String nationalityName) { this.nationalityName = nationalityName; }
}