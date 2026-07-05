package oop;

public class Employee extends Person {

    private String department;

    public Employee(int id, String fullName, String department) {
        super(id, fullName);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public void displayInfo() {
        System.out.println("Employee: " + getFullName());
    }
}