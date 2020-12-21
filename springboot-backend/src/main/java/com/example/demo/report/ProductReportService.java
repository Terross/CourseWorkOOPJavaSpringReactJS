package com.example.demo.report;


import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductReportService {
    @Autowired
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductReportService.class);

    @Async("taskExecutor")
    public CompletableFuture<Void> exportPDFReport() throws FileNotFoundException, JRException {
        logger.info("The pdf report creation started");
        logger.info("1123");
        Iterable<Product> productsIter = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productsIter.forEach(products::add);
        logger.info(products.get(0).getName());
        try
        {
            File file  = ResourceUtils.getFile("classpath:productsReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/products.pdf");

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> exportHTMLReport() throws FileNotFoundException, JRException {
        logger.info("The html report creation started");
        Iterable<Product> productsIter = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productsIter.forEach(products::add);
        File file  = ResourceUtils.getFile("classpath:productsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/products.html");

        return null;
    }
}
