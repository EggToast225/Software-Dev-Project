package com.example.sql_dbms_ui.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.sql_dbms_ui.Models.Payroll;

public interface PayrollRepo extends JpaRepository<Payroll, Long>{

    @Query("SELECT j.title as jobTitle, " +
    "SUM(p.earnings - p.fed_tax - p.fed_med - p.fed_SS - p.state_tax - p.retire_401k - p.healthCare) as netPay " +
    "FROM Payroll p " +
    "JOIN p.employee e " +
    "JOIN EmployeeJobTitle ej ON e.empid = ej.empId " +
    "JOIN ej.jobTitle j " +
    "WHERE YEAR(p.payDate) = :year AND MONTH(p.payDate) = :month " +
    "GROUP BY j.title")
    List<Map<String, Object>> findTotalMonthlyEarningsByJobTitle(@Param("year") int year, @Param("month") int month);

    @Query("SELECT d.name as division, " +
    "SUM(p.earnings - p.fed_tax - p.fed_med - p.fed_SS - p.state_tax - p.retire_401k - p.healthCare) as netPay " +
    "FROM Payroll p " +
    "JOIN p.employee e " +
    "JOIN EmployeeDivision ed ON e.empid = ed.empId " +
    "JOIN ed.division d " +
    "WHERE YEAR(p.payDate) = :year AND MONTH(p.payDate) = : month " +
    "GROUP BY d.name")
    List<Map<String, Object>> findTotalMonthlyEarningsByDivision(@Param("year") int year, @Param("month") int month);
    

    List<Payroll> findByEmployeeEmpidOrderByPayDateDesc(Long empid);
    List<Payroll> findByEmployeeEmpidOrderByPayDateAsc(Long empid);
    List<Payroll> findAllByOrderByEmployeeEmpidAscPayDateAsc();
    
}