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
    private String phone;
    private String address;
    private String department;



    /**
     * @desc Constructor that initializes the values
     * @param id Employee ID
     * @param name Employee name
     * @param salary Salary of employee
     * @param startDate Start Date
     * @param phone Employee Phone No.
     * @param address Employee address
     * @param department Employee department
     */
    public EmployeePayrollDetails(int id, String name, double salary, String startDate,String phone,String address,String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
        this.phone = phone;
        this.address = address;
        this.department = department;
    }

    /**
     * @desc Returns a string representation of the EmployeePayrollDetails object.
     * @return A string containing the details of the employee, including id, name, salary,
     *         start date, phone, address, and department.
     */
    @Override
    public String toString() {
        return "EmployeePayrollDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate='" + startDate + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    /**
     * @desc Indicates whether some other object is "equal to" this one.
     * @param o The reference object with which to compare.
     * @return true if this object is the same as the obj argument otherwise false.
     */
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

    /**
     * @desc Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, startDate);
    }
}
