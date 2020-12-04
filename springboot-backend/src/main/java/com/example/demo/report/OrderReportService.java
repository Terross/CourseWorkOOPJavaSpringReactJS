package com.example.demo.report;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderReportService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderReportService.class);

    @Async("taskExecutor")
    public CompletableFuture<Void> exportPDFReport() throws FileNotFoundException, JRException {
        logger.info("The pdf report creation started");
        List<Order> orders = orderRepository.findAll();

        File file  = ResourceUtils.getFile("classpath:ordersReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orders);
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/orders.pdf");

        } catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> exportHTMLReport() throws FileNotFoundException, JRException {
        logger.info("The html report creation started");
        Iterable<Order> ordersIter = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        ordersIter.forEach(orders::add);
        File file  = ResourceUtils.getFile("classpath:ordersReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orders);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                "/home/dmitry/Documents/CourseWork/react-frontend/src/PdfReports/orders.html");

        return null;
    }
}
