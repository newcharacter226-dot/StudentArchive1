package oop;

public class Admin extends Employee implements Searchable {

    public Admin(int id, String fullName, String department) {
        super(id, fullName, department);
    }

    @Override
    public void search(int id) {
        System.out.println("Searching for ID: " + id);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getFullName());
    }
}