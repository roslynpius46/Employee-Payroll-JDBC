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
            System.out.println();

            // Calculate sum of salaries by gender
            double[] sumOfSalaries = payrollService.calculateSumOfSalariesByGender();
            System.out.println("Sum of salaries for Male employees: " + sumOfSalaries[0]);
            System.out.println("Sum of salaries for Female employees: " + sumOfSalaries[1]);
            System.out.println();

            // Calculate average of salaries by gender
            double[] avgOfSalaries = payrollService.calculateAverageOfSalariesByGender();
            System.out.println("Average of salaries for Male employees: " + avgOfSalaries[0]);
            System.out.println("Average of salaries for Female employees: " + avgOfSalaries[1]);
            System.out.println();

            // Calculate minimum of salaries by gender
            double[] minOfSalaries = payrollService.calculateMinSalariesByGender();
            System.out.println("Minimum  of salaries for Male employees: " + minOfSalaries[0]);
            System.out.println("Minimum of salaries for Female employees: " + minOfSalaries[1]);
            System.out.println();

            // Calculate maximum of salaries by gender
            double[] maxOfSalaries = payrollService.calculateMaxSalariesByGender();
            System.out.println("Maximum  of salaries for Male employees: " + maxOfSalaries[0]);
            System.out.println("Maximum of salaries for Female employees: " + maxOfSalaries[1]);
            System.out.println();

            // Calculate count of employees by gender
            int[] countByGender = payrollService.calculateCountByGender();
            System.out.println("Number of Male employees: " + countByGender[0]);
            System.out.println("Number of Female employees: " + countByGender[1]);

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
