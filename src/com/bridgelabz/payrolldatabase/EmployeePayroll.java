package com.bridgelabz.payrolldatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @desc Service class for Employee Payroll
 */
public class EmployeePayroll {

    private Connection connection;
    public EmployeePayroll(Connection connection)
    {
        this.connection=connection;
    }

    /**
     * @desc Retrieve Employee Payroll Data from the Database
     * @return List of Employee Payroll Data
     * @throws PayrollDBException if there is an error in database operations
     */
    public List<EmployeePayrollDetails> getEmployeePayrollData() throws PayrollDBException {
        List<EmployeePayrollDetails> employeePayrolls = new ArrayList<>();
        //Connection connection = DBConnection.getConnection();
        try {
            String sql = "SELECT * FROM employee_payroll";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String startDate = resultSet.getString("start_date");
                employeePayrolls.add(new EmployeePayrollDetails(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error retrieving Employee Payroll data", e);
        }
        return employeePayrolls;
    }

    /**
     * @desc Update the salary for an employee in the database
     * @param employeeName Employee Name
     * @param newSalary New salary value
     * @throws PayrollDBException if there is an error in database operations
     */
    public void updateEmployeeSalary(String employeeName, double newSalary) throws PayrollDBException {
        try {
            String updateSql = "UPDATE employee_payroll SET salary = ? WHERE name = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setDouble(1, newSalary);
                updateStatement.setString(2, employeeName);
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Salary updated successfully for Employee: " + employeeName);
                } else {
                    throw new PayrollDBException("Failed to update salary. Employee ID not found: " + employeeName);
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error updating Employee Payroll data", e);
        }
    }

    /**
     * @desc Compare Employee Payroll Object with DB
     * @param employeeName Employee Name
     * @return true if the data in the object matches the data in the database, false otherwise
     * @throws PayrollDBException if there is an error in database operations
     */
    public boolean compareEmployeeWithDB(String employeeName) throws PayrollDBException {
        try {
            String selectSql = "SELECT * FROM employee_payroll WHERE name = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setString(1, employeeName);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    String startDate = resultSet.getString("start_date");

                    EmployeePayrollDetails dbEmployee = new EmployeePayrollDetails(id, name, salary, startDate);

                    return Objects.equals(getEmployeeDetails(employeeName), dbEmployee);
                } else {
                    throw new PayrollDBException("Employee not found in the database. Employee Name: " + employeeName);
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error comparing Employee Payroll data with DB", e);
        }
    }

    /**
     * @desc Retrieve details of an employee from the database based on the employee ID
     * @param employeeName Employee ID
     * @return EmployeePayrollDetails object if the employee is found, null otherwise
     * @throws PayrollDBException if there is an error in database operations
     */
    public EmployeePayrollDetails getEmployeeDetails(String employeeName) throws PayrollDBException {
        try {
            String selectSql = "SELECT * FROM employee_payroll WHERE name = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setString(1, employeeName);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    String startDate = resultSet.getString("start_date");
                    return new EmployeePayrollDetails(id, name, salary, startDate);
                } else {
                    return null; // Employee not found in the database
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error retrieving Employee Payroll details from the database", e);
        }
    }

    /**
     * @desc Retrieve employees who have joined in a particular date range from the database
     * @param startDate Start date of the range
     * @param endDate   End date of the range
     * @return List of EmployeePayrollDetails objects within the specified date range
     * @throws PayrollDBException if there is an error in database operations
     */
    public List<EmployeePayrollDetails> getEmployeesByDateRange(String startDate, String endDate) throws PayrollDBException {
        List<EmployeePayrollDetails> employeesInRange = new ArrayList<>();
        try {
            String selectSql = "SELECT * FROM employee_payroll WHERE start_date BETWEEN ? AND ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setString(1, startDate);
                selectStatement.setString(2, endDate);
                ResultSet resultSet = selectStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    String joinDate = resultSet.getString("start_date");
                    employeesInRange.add(new EmployeePayrollDetails(id, name, salary, joinDate));
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error retrieving employees by date range from the database", e);
        }
        return employeesInRange;
    }
}
