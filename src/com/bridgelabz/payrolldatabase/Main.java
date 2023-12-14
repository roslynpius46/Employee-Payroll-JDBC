package com.bridgelabz.payrolldatabase;

import java.sql.Connection;
import java.util.List;

/**
 * @desc Main class for payroll Database
 */
public class Main {

    public static void main(String[] args) {
        // Get a database connection
        Connection connection = DBConnection.getConnection();

        // Test Employee Payroll Service
        try {
            EmployeePayroll payrollService = new EmployeePayroll(connection);
            List<EmployeePayrollDetails> employeePayrolls = payrollService.getEmployeePayrollData();
            for (EmployeePayrollDetails payroll : employeePayrolls) {
                System.out.println(payroll);
            }

            // Update salary for Employee with ID 1
            payrollService.updateEmployeeSalary("Terissa", 3000000.00);

        } catch (PayrollDBException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally {
            // Close the database connection after retrieval
            DBConnection.closeConnection();
        }
    }
}
