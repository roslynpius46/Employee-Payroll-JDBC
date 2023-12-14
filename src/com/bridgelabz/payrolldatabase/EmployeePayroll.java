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
        try {
            String sql = "SELECT * FROM employee_payroll";
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    String startDate = resultSet.getString("start_date");
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    String department = resultSet.getString("department");
                    employeePayrolls.add(new EmployeePayrollDetails(id, name, salary, startDate, phone, address, department));
                }
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
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    String department = resultSet.getString("department");

                    EmployeePayrollDetails dbEmployee = new EmployeePayrollDetails(id, name, salary, startDate, phone, address, department);

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
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    String department = resultSet.getString("department");
                    return new EmployeePayrollDetails(id, name, salary, startDate, phone, address, department);
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
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    String department = resultSet.getString("department");
                    employeesInRange.add(new EmployeePayrollDetails(id, name, salary, joinDate, phone, address, department));
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error retrieving employees by date range from the database", e);
        }
        return employeesInRange;
    }

    /**
     * @desc Calculate the sum of salaries for male and female employees
     * @return Array with two elements: sum of salaries for male employees and sum for female employees
     * @throws PayrollDBException if there is an error in database operations
     */
    public double[] calculateSumOfSalariesByGender() throws PayrollDBException {
        double[] sumOfSalaries = new double[2];

        try {
            String sql = "SELECT gender, SUM(salary) FROM employee_payroll GROUP BY gender";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String gender = resultSet.getString("gender");
                    double sum = resultSet.getDouble(2);

                    if ("M".equals(gender)) {
                        sumOfSalaries[0] = sum; // Male
                    } else if ("F".equals(gender)) {
                        sumOfSalaries[1] = sum; // Female
                    }
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error calculating sum of salaries by gender", e);
        }

        return sumOfSalaries;
    }

    /**
     * @desc Calculate the average of salaries for male and female employees
     * @return Array with two elements: average of salaries for male employees and average for female employees
     * @throws PayrollDBException if there is an error in database operations
     */
    public double[] calculateAverageOfSalariesByGender() throws PayrollDBException {
        double[] averageSalaries = new double[2];

        try {
            String sql = "SELECT gender, AVG(salary) FROM employee_payroll GROUP BY gender";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String gender = resultSet.getString("gender");
                    double average = resultSet.getDouble(2);

                    if ("M".equals(gender)) {
                        averageSalaries[0] = average; // Male
                    } else if ("F".equals(gender)) {
                        averageSalaries[1] = average; // Female
                    }
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error calculating average salaries by gender", e);
        }

        return averageSalaries;
    }

    /**
     * @desc Calculate the minimum salary for male and female employees
     * @return Array with two elements: minimum salary for male employees and minimum salary for female employees
     * @throws PayrollDBException if there is an error in database operations
     */
    public double[] calculateMinSalariesByGender() throws PayrollDBException {
        double[] minSalaries = new double[2];

        try {
            String sql = "SELECT gender, MIN(salary) FROM employee_payroll GROUP BY gender";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String gender = resultSet.getString("gender");
                    double minSalary = resultSet.getDouble(2);

                    if ("M".equals(gender)) {
                        minSalaries[0] = minSalary; // Male
                    } else if ("F".equals(gender)) {
                        minSalaries[1] = minSalary; // Female
                    }
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error calculating minimum salaries by gender", e);
        }

        return minSalaries;
    }

    /**
     * @desc Calculate the maximum salary for male and female employees
     * @return Array with two elements: maximum salary for male employees and maximum salary for female employees
     * @throws PayrollDBException if there is an error in database operations
     */
    public double[] calculateMaxSalariesByGender() throws PayrollDBException {
        double[] maxSalaries = new double[2];

        try {
            String sql = "SELECT gender, MAX(salary) FROM employee_payroll GROUP BY gender";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String gender = resultSet.getString("gender");
                    double maxSalary = resultSet.getDouble(2);

                    if ("M".equals(gender)) {
                        maxSalaries[0] = maxSalary; // Male
                    } else if ("F".equals(gender)) {
                        maxSalaries[1] = maxSalary; // Female
                    }
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error calculating maximum salaries by gender", e);
        }

        return maxSalaries;
    }

    /**
     * @desc Calculate the count of male and female employees
     * @return Array with two elements: count of male employees and count of female employees
     * @throws PayrollDBException if there is an error in database operations
     */
    public int[] calculateCountByGender() throws PayrollDBException {
        int[] countByGender = new int[2];

        try {
            String sql = "SELECT gender, COUNT(*) FROM employee_payroll GROUP BY gender";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String gender = resultSet.getString("gender");
                    int count = resultSet.getInt(2);

                    if ("M".equals(gender)) {
                        countByGender[0] = count; // Male
                    } else if ("F".equals(gender)) {
                        countByGender[1] = count; // Female
                    }
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Error calculating count by gender", e);
        }

        return countByGender;
    }

}
