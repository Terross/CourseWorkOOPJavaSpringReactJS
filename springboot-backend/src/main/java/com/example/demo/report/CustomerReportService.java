package com.example.demo.report;

import com.example.demo.AsyncConfiguration;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
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

@Service
public class CustomerReportService {
    @Autowired
    private CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Async
    public void exportReport() throws FileNotFoundException, JRException {
        List<Customer> customers = customerRepository.findAll();
        File file  = ResourceUtils.getFile("classpath:customersReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/customers.pdf");
    }
}
