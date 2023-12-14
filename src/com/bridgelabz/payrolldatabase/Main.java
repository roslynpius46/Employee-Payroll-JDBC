package com.bridgelabz.payrolldatabase;

import java.sql.Connection;

/**
 * @desc Main class for payroll Database
 */
public class Main {

    public static void main(String[] args) {
        // Get a database connection
        Connection connection = DBConnection.getConnection();
        DBConnection.closeConnection();

    }
}
