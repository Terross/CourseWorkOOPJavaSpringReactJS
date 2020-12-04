package com.example.demo.report;

import com.example.demo.AsyncConfiguration;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
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
public class CustomerReportService {
    @Autowired
    private CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerReportService.class);

    @Async("taskExecutor")
    public CompletableFuture<Void> exportPDFReport() throws FileNotFoundException, JRException {
        logger.info("The pdf report creation started");
        List<Customer> customers = customerService.getAllCustomers();
        logger.info(customers.get(0).toString());
        File file  = ResourceUtils.getFile("classpath:customersReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/customers.pdf");

        return null;
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> exportHTMLReport() throws FileNotFoundException, JRException {
        logger.info("The html report creation started");
        List<Customer> customers = customerService.getAllCustomers();
        File file  = ResourceUtils.getFile("classpath:customersReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint,
                "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/customers.html");
        return null;
    }
}
