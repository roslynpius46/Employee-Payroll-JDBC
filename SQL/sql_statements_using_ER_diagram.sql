-- Implement the ER Diagram into Payroll Service DB
USE payroll_service;

-- USE CASE 11: Creating Tables given in the ER diagram
-- Company Table
CREATE TABLE company (
    company_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(15)
);
INSERT INTO company (company_id, name, address, phone_number)
VALUES
(1, 'M Technologies', 'Gandhi Nagar, Street 2', '99223-91642'),
(2, 'DInnovations', 'Nehru Street, Lane 2', '92527-37913'),
(3, 'B Enterprises', 'Vidyalaya Road, Sector 3', '82461-99752');

-- Department Table
CREATE TABLE department (
    department_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

INSERT INTO department (department_id, name, location)
VALUES
    (1, 'Finance','BLR'),
    (2, 'Tech','HYD');

-- Employee Table
CREATE TABLE employee (
    employee_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    start_date DATE NOT NULL,
    gender CHAR(1),
    date_of_birth DATE,
    company_id INT,
    FOREIGN KEY (company_id) REFERENCES company(company_id),
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);
INSERT INTO employee (employee_id, name, salary, start_date, gender, date_of_birth,company_id,department_id)
VALUES
    (1, 'Rajesh Sharma', 56000.00, '2023-03-01', 'M', '1995-10-13',1,1),
	(2, 'Priya Patel', 87000.00, '2023-02-02', 'F', '1994-09-12',2,2),
	(3, 'Amit Kapoor', 30000.00, '2023-02-28', 'M', '1993-03-08',3,2),
	(4, 'Anjali Gupta', 42000.00, '2023-05-14', 'F', '1992-12-04',3,2),
	(5, 'Vikram Singh', 55000.00, '2023-03-05', 'M', '1995-12-30',1,1);
    
-- Payroll Table
CREATE TABLE payroll (
    payroll_id INT PRIMARY KEY,
    salary DECIMAL(10,2) NOT NULL,
    start_date DATE NOT NULL,
    basic_pay DECIMAL(10,2),
    deductions DECIMAL(10,2),
    taxable_pay DECIMAL(10,2),
	income_tax DECIMAL(10,2),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

INSERT INTO payroll (payroll_id, salary, start_date, basic_pay, deductions, taxable_pay, income_tax, employee_id)
VALUES
    (1, 60000.00, '2023-03-01', 55000.00, 5000.00, 50000.00, 2000.00, 1),
    (2, 70000.00, '2023-02-02', 65000.00, 5000.00, 60000.00, 2500.00, 2),
    (3, 80000.00, '2023-02-28', 72000.00, 8000.00, 64000.00, 3000.00, 3),
    (4, 75000.00, '2023-05-14', 70000.00, 5000.00, 65000.00, 2200.00, 4),
    (5, 90000.00, '2023-03-05', 85000.00, 7000.00, 78000.00, 3500.00, 5);

-- USE CASE 12: Refactor USE CASE 4,5,7
-- USE CASE 4:
SELECT * FROM payroll;

-- USE CASE 5:
SELECT e.name,p.salary
FROM  payroll p
JOIN employee e ON e.employee_id = p.employee_id
WHERE e.name = 'Priya Patel';

SELECT * 
FROM payroll 
WHERE start_date BETWEEN CAST('2023-03-05' AS DATE) AND NOW();

-- Use Case 7:
-- Sum of salary by gender
SELECT e.gender, SUM(p.salary) AS TotalSalary
FROM employee e
JOIN payroll p ON e.employee_id = p.employee_id
GROUP BY e.gender;

-- Average of salary by gender
SELECT e.gender , AVG(p.salary) AS AverageSalary
FROM employee e
JOIN payroll p ON e.employee_id= p.employee_id
GROUP BY e.gender;

-- Minimum of salary by gender
SELECT e.gender , MIN(p.salary) AS MinSalary
FROM employee e
JOIN payroll p ON e.employee_id= p.employee_id
GROUP BY e.gender;

-- Maximum of salary by gender
SELECT e.gender , MAX(p.salary) AS MaxSalary
FROM employee e
JOIN payroll p ON e.employee_id= p.employee_id
GROUP BY e.gender;

SELECT e.gender , COUNT(*) AS NumberOfFemale
FROM employee e
JOIN payroll p ON e.employee_id= p.employee_id
GROUP BY e.gender;