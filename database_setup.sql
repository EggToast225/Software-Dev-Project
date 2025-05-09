-- Create database
CREATE DATABASE IF NOT EXISTS employee_management;
USE employee_management;

-- Create tables
CREATE TABLE IF NOT EXISTS state (
    state_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    state_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS city (
    city_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    city_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS address (
    address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(255),
    zip VARCHAR(10),
    city_id BIGINT NOT NULL,
    state_id BIGINT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(city_id),
    FOREIGN KEY (state_id) REFERENCES state(state_id)
);

CREATE TABLE IF NOT EXISTS employees (
    empid BIGINT PRIMARY KEY AUTO_INCREMENT,
    Fname VARCHAR(255),
    Lname VARCHAR(255),
    email VARCHAR(255),
    HireDate DATE,
    Salary DOUBLE,
    SSN VARCHAR(11),
    address_id BIGINT,
    gender VARCHAR(50),
    identified_race VARCHAR(50),
    DOB DATE,
    phone VARCHAR(12),
    FOREIGN KEY (address_id) REFERENCES address(address_id)
);

CREATE TABLE IF NOT EXISTS division (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255),
    city VARCHAR(255),
    addressLine1 VARCHAR(255),
    addressLine2 VARCHAR(255),
    state VARCHAR(255),
    country VARCHAR(255),
    postalCode VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS job_title (
    job_title_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS employee_division (
    empid BIGINT,
    div_ID BIGINT,
    PRIMARY KEY (empid, div_ID),
    FOREIGN KEY (empid) REFERENCES employees(empid) ON DELETE CASCADE,
    FOREIGN KEY (div_ID) REFERENCES division(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS employee_job_title (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empid BIGINT,
    job_title_id BIGINT,
    start_date DATE NOT NULL,
    end_date DATE,
    is_primary BOOLEAN DEFAULT true,
    FOREIGN KEY (empid) REFERENCES employees(empid) ON DELETE CASCADE,
    FOREIGN KEY (job_title_id) REFERENCES job_title(job_title_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payroll (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    empid BIGINT,
    job_title_id BIGINT,
    pay_date DATE,
    earnings DOUBLE,
    fed_tax DOUBLE,
    fed_med DOUBLE,
    fed_SS DOUBLE,
    state_tax DOUBLE,
    retire_401k DOUBLE,
    health_care DOUBLE,
    FOREIGN KEY (empid) REFERENCES employees(empid) ON DELETE CASCADE,
    FOREIGN KEY (job_title_id) REFERENCES job_title(job_title_id)
);

-- Insert sample data
-- States
INSERT INTO state (state_name) VALUES 
('California'),
('New York'),
('Texas');

-- Cities
INSERT INTO city (city_name) VALUES 
('San Francisco'),
('New York City'),
('Austin');

-- Addresses
INSERT INTO address (street, zip, city_id, state_id) VALUES 
('123 Tech Ave', '94105', 1, 1),
('456 Finance St', '10004', 2, 2),
('789 Startup Blvd', '78701', 3, 3);

-- Job Titles
INSERT INTO job_title (title) VALUES 
('Software Engineer'),
('Product Manager'),
('HR Manager');

-- Divisions
INSERT INTO division (Name, city, addressLine1, state, country, postalCode) VALUES 
('Engineering', 'San Francisco', '100 Tech Plaza', 'CA', 'USA', '94105'),
('Product', 'New York City', '200 Innovation Center', 'NY', 'USA', '10004'),
('Human Resources', 'Austin', '300 People Place', 'TX', 'USA', '78701');

-- Employees
INSERT INTO employees (Fname, Lname, email, HireDate, Salary, SSN, address_id, gender, identified_race, DOB, phone) VALUES 
('John', 'Doe', 'john.doe@company.com', '2023-01-15', 120000.00, '123-45-6789', 1, 'Male', 'White', '1990-05-20', '123-456-7890'),
('Jane', 'Smith', 'jane.smith@company.com', '2023-02-01', 110000.00, '234-56-7890', 2, 'Female', 'Asian', '1992-08-15', '234-567-8901'),
('Bob', 'Johnson', 'bob.johnson@company.com', '2023-03-01', 95000.00, '345-67-8901', 3, 'Male', 'Black', '1988-12-10', '345-678-9012');

-- Employee Job Titles with start dates
INSERT INTO employee_job_title (empid, job_title_id, start_date, is_primary) VALUES 
(1, 1, '2023-01-15', true),  -- John Doe - Software Engineer
(2, 2, '2023-02-01', true),  -- Jane Smith - Product Manager
(3, 3, '2023-03-01', true);  -- Bob Johnson - HR Manager

-- Employee Divisions
INSERT INTO employee_division (empid, div_ID) VALUES 
(1, 1),  -- John Doe - Engineering
(2, 2),  -- Jane Smith - Product
(3, 3);  -- Bob Johnson - HR

-- Payroll (3 months of data for each employee)
INSERT INTO payroll (empid, job_title_id, pay_date, earnings, fed_tax, fed_med, fed_SS, state_tax, retire_401k, health_care) VALUES 
-- John Doe's payroll
(1, 1, '2024-01-15', 10000.00, 2200.00, 145.00, 620.00, 800.00, 600.00, 200.00),
(1, 1, '2024-02-15', 10000.00, 2200.00, 145.00, 620.00, 800.00, 600.00, 200.00),
(1, 1, '2024-03-15', 10000.00, 2200.00, 145.00, 620.00, 800.00, 600.00, 200.00),

-- Jane Smith's payroll
(2, 2, '2024-01-15', 9166.67, 2017.00, 133.00, 568.33, 733.33, 550.00, 200.00),
(2, 2, '2024-02-15', 9166.67, 2017.00, 133.00, 568.33, 733.33, 550.00, 200.00),
(2, 2, '2024-03-15', 9166.67, 2017.00, 133.00, 568.33, 733.33, 550.00, 200.00),

-- Bob Johnson's payroll
(3, 3, '2024-01-15', 7916.67, 1741.67, 114.79, 490.83, 633.33, 475.00, 200.00),
(3, 3, '2024-02-15', 7916.67, 1741.67, 114.79, 490.83, 633.33, 475.00, 200.00),
(3, 3, '2024-03-15', 7916.67, 1741.67, 114.79, 490.83, 633.33, 475.00, 200.00);

-- Create indexes for better performance
CREATE INDEX idx_employee_email ON employees(email);
CREATE INDEX idx_employee_ssn ON employees(SSN);
CREATE INDEX idx_payroll_date ON payroll(pay_date);
CREATE INDEX idx_payroll_empid ON payroll(empid);
CREATE INDEX idx_payroll_job_title ON payroll(job_title_id);
CREATE INDEX idx_emp_job_title_dates ON employee_job_title(start_date, end_date);
CREATE INDEX idx_emp_job_title_current ON employee_job_title(empid, job_title_id, is_primary); 