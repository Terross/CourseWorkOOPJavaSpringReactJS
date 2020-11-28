package com.example.demo.report;

import com.example.demo.model.Employee;
import com.example.demo.model.Product;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductReportService {

    @Autowired
    private ProductRepository productRepository;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        Iterable<Product> productsIter = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productsIter.forEach(products::add);
        File file  = ResourceUtils.getFile("classpath:productsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy","Dmitry");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "./products.html");
            return "report generated";
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, "./products.pdf");
            return "report generated";
        }
        return "report not generated";
    }
}
