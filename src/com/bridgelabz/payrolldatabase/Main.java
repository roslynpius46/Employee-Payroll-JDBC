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
            System.out.println();

            // Update salary for Employee with ID 1
            payrollService.updateEmployeeSalary("Terissa", 3000000.00);
            System.out.println();

            String startDate="2023-02-1";
            String endDate="2023-03-31";
            List<EmployeePayrollDetails> employeesInRange = payrollService.getEmployeesByDateRange(startDate, endDate);
            for (EmployeePayrollDetails employee : employeesInRange) {
                System.out.println(employee);
            }

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
