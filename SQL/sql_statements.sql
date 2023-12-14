-- USE CASE 1: Ability to create a payroll service database

CREATE DATABASE payroll_service;
SHOW DATABASES;
USE payroll_service;

-- USE CASE 2: Ability to create a employee payroll table in the payroll service database
CREATE TABLE employee_payroll (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    start_date DATE NOT NULL
);

-- USE CASE 3: Ability to create employee payroll data
INSERT INTO employee_payroll (name, salary, start_date) VALUES
('Roslyn Pius', 50000.00, '2023-01-01'),
('Riya A', 60000.00, '2023-02-15'),
('Joshua H', 45000.00, '2023-03-10');

-- USE CASE 4: Retrieve the records of the table
SELECT * FROM employee_payroll;

-- USE CASE 5: Adding Gender Field
ALTER TABLE employee_payroll
ADD gender CHAR(1);
SET SQL_SAFE_UPDATES = 0;

-- Update Gender Information
UPDATE employee_payroll
SET gender = 'M'
WHERE name = 'Joshua H';

UPDATE employee_payroll
SET gender = 'F'
WHERE name = 'Roslyn Pius' or name='Riya A';

-- View Table to see changes
SELECT * FROM employee_payroll;

-- USE CASE 6: Retrieve salary data for a particular employee
SELECT salary FROM employee_payroll WHERE name = 'Roslyn Pius';

-- All employees who have joined in a particular data range
SELECT * FROM employee_payroll
WHERE start_date BETWEEN CAST('2023-02-01' AS DATE) AND now();

-- USE CASE 7: 
-- Sum of Salary by Gender
SELECT gender, SUM(salary) AS total_salary
FROM employee_payroll
GROUP BY gender;

-- Average Salary by Gender
SELECT gender, AVG(salary) AS average_salary
FROM employee_payroll
GROUP BY gender;

-- Minimum Salary by Gender
SELECT gender, MIN(salary) AS min_salary
FROM employee_payroll
GROUP BY gender;

-- Maximum Salary by Gender
SELECT gender, MAX(salary) AS max_salary
FROM employee_payroll
GROUP BY gender;

-- Number of Male and Female Employees
SELECT gender, COUNT(*) AS employee_count
FROM employee_payroll
GROUP BY gender;

-- USE CASE 8: Add phone, address, and department columns to the employee_payroll table
ALTER TABLE employee_payroll
ADD phone VARCHAR(20),
ADD address VARCHAR(255) DEFAULT 'Not Available',
ADD department VARCHAR(50) NOT NULL;

-- USE CASE 9: Add Basic Pay, Deductions, Taxable Pay, Income Tax, Net Pay columns
ALTER TABLE employee_payroll
ADD basic_pay DECIMAL(10,2),
ADD deductions DECIMAL(10,2),
ADD taxable_pay DECIMAL(10,2),
ADD income_tax DECIMAL(10,2),
ADD net_pay DECIMAL(10,2);

-- USE CASE 10: make Terissa as part of Sales and Marketing Department
UPDATE employee_payroll
SET department = 'Sales and Marketing'
WHERE name = 'Terissa';

select * from employee_payroll;
