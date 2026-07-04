package main;

import java.sql.Connection;
import database.DBConnection;

public class TestConnection {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("==============================");
            System.out.println("Connection Successful");
            System.out.println("==============================");
        } else {
            System.out.println("==============================");
            System.out.println("Connection Failed");
            System.out.println("==============================");
        }
    }
}