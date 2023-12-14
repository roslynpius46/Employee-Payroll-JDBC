package com.bridgelabz.payrolldatabase;

/**
 * @desc Class representing Employee Payroll
 */
public class EmployeePayrollDetails {

    private int id;
    private String name;
    private double salary;
    private String startDate;

    /**
     * @desc Constructor that initializes the values
     * @param id Employee ID
     * @param name Employee name
     * @param salary Salary of employee
     * @param startDate Start Date
     */
    public EmployeePayrollDetails(int id, String name, double salary, String startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "EmployeePayroll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
