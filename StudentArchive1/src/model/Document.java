package model;

public class Document {

    private int documentId;
    private int studentId;
    private String documentName;
    private String documentDate;
    private String filePath;

    public Document() {
    }

    public Document(int documentId, int studentId, String documentName, String documentDate, String filePath) {
        this.documentId = documentId;
        this.studentId = studentId;
        this.documentName = documentName;
        this.documentDate = documentDate;
        this.filePath = filePath;
    }

    public int getDocumentId() { return documentId; }
    public void setDocumentId(int documentId) { this.documentId = documentId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getDocumentName() { return documentName; }
    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public String getDocumentDate() { return documentDate; }
    public void setDocumentDate(String documentDate) { this.documentDate = documentDate; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}