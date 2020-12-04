package com.example.demo.report;

import com.example.demo.AsyncConfiguration;
import com.example.demo.model.Customer;
import com.example.demo.model.Employee;
import com.example.demo.service.CustomerService;
import com.example.demo.service.EmployeeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeReportService {
    @Autowired
    private EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeReportService.class);

    @Async("taskExecutor")
    public CompletableFuture<Void> exportPDFReport() throws FileNotFoundException, JRException {
        logger.info("The pdf report creation started");
        List<Employee> employees = employeeService.getAllEmployees();
        File file  = ResourceUtils.getFile("classpath:employeesReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/employees.pdf");

        return null;
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> exportHTMLReport() throws FileNotFoundException, JRException {
        logger.info("The html report creation started");
        List<Employee> employees = employeeService.getAllEmployees();
        File file  = ResourceUtils.getFile("classpath:employeesReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint,
                "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/employees.html");
        return null;
    }
}
