package com.example.sql_dbms_ui.repo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sql_dbms_ui.Models.Payroll;

public interface PayrollRepo extends JpaRepository<Payroll, Long> {

    // Fetch all payrolls ordered by employee.empid and payDate
    List<Payroll> findAllByOrderByEmployeeEmpidAscPayDateAsc();

    // Fetch payroll history for a specific employee ordered by payDate ASC
    List<Payroll> findByEmployeeEmpidOrderByPayDateAsc(Long empid);

    // Fetch payroll history for a specific employee ordered by payDate DESC
    List<Payroll> findByEmployeeEmpidOrderByPayDateDesc(Long empid);

    // Find payroll entries by year and month
    default List<Payroll> findByYearAndMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.set(year, month - 1, cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date endDate = cal.getTime();
        
        return findByPayDateBetween(startDate, endDate);
    }

    // Find payroll entries between two dates
    List<Payroll> findByPayDateBetween(Date startDate, Date endDate);

    // Total monthly net pay by job title using the date range method
    @Query("SELECT jt.title, " +
           "SUM(p.earnings) as earnings " +
           "FROM Payroll p " +
           "JOIN p.employee e " +
           "JOIN EmployeeJobTitle ejt ON ejt.empId = e.empid " +
           "JOIN ejt.jobTitle jt " +
           "WHERE p.payDate BETWEEN :startDate AND :endDate " +
           "GROUP BY jt.title")
    List<Map<String, Object>> findTotalMonthlyEarningsByJobTitle(
        @Param("startDate") Date startDate, 
        @Param("endDate") Date endDate
    );

    // Total monthly net pay by division
    @Query("SELECT d.name as division, " +
           "SUM(p.earnings) as earnings " +
           "FROM Payroll p " +
           "JOIN p.employee e " +
           "JOIN EmployeeDivision ed ON ed.empId = e.empid " +
           "JOIN ed.division d " +
           "WHERE p.payDate BETWEEN :startDate AND :endDate " +
           "GROUP BY d.name")
    List<Map<String, Object>> findTotalMonthlyEarningsByDivision(
        @Param("startDate") Date startDate, 
        @Param("endDate") Date endDate
    );
}