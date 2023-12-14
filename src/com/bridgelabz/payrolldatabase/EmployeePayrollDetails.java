package com.bridgelabz.payrolldatabase;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollDetails that = (EmployeePayrollDetails) o;
        return id == that.id &&
                Double.compare(that.salary, salary) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, startDate);
    }
}
