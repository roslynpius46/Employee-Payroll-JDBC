package com.bridgelabz.payrolldatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc Service class for Employee Payroll
 */
public class EmployeePayroll {

    private Connection connection;
    EmployeePayroll(Connection connection)
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
}
