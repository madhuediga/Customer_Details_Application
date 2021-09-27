package com.cassandra.example.controller;

import com.cassandra.example.export.GeneratePdfReport;
import com.cassandra.example.export.UserExcelExporter;
import com.cassandra.example.model.Customer;
import com.cassandra.example.service.CustomerService;
import javafx.scene.input.KeyCode;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader int conversationId,@Valid @RequestBody Customer customer){
        Integer save=service.createCustomer(customer,conversationId);
        return  new ResponseEntity<>(customer.getBillingAccountNumber(),HttpStatus.OK);
    }

    @PutMapping("/updateByBillingAccount/{billingAccountNumber}")
    public ResponseEntity<?> updateCustomer( @RequestBody Customer customer,@PathVariable int billingAccountNumber){
        Customer update=service.updateCustomer(customer,billingAccountNumber);
        return new ResponseEntity<>("updated success",HttpStatus.OK);
    }
    @PutMapping("/updateByPhone/{phoneNumber}")
    public ResponseEntity<?> updateCustomerByPhoneNumber(@RequestBody Customer customer,@PathVariable String phoneNumber){
        Customer update=service.updateCustomerByPhone(customer,phoneNumber);
        return new ResponseEntity<>("updated success",HttpStatus.OK);
    }
    @GetMapping(value = "getbyBillingAccNo/{billingAccountNumber}")
    public ResponseEntity<?> getUsingbillingAccNo(@PathVariable int billingAccountNumber) {
        Customer find=service.getBybillingAccNo(billingAccountNumber);
            return new ResponseEntity<>(find,HttpStatus.OK);


    }

    @GetMapping(value = "getCustomerByPhone/{Phno}")
    public ResponseEntity<?> getUsingPhoneNUmber(@PathVariable String Phno) {
       Optional<Customer> find=service.getByphoneNumber(Phno);
       if(find.isPresent()){
           return new ResponseEntity<>(find,HttpStatus.OK);
       }
       return new ResponseEntity<>(find,HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping(value = "deleteByBillingAccountNo/{billingAccountNumber}")
    public ResponseEntity<?> deleteByUsingBillingNO(@PathVariable int billingAccountNumber){
        boolean delete=service.deleteByBillingNo(billingAccountNumber);
        if (delete){
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("failed to delete",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "deleteByPhoneNo/{phoneNumber}")
    public ResponseEntity<?> deleteByUsingphoneNO(@PathVariable String phoneNumber){
        boolean delete=service.deleteByphoneNumber(phoneNumber);
        if (delete){
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("failed to delete",HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="exportInToExcel")
    public void exportToExcel(HttpServletResponse response)throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Customer> listCustomers = service.findAll();

        UserExcelExporter excelExporter = new UserExcelExporter(listCustomers);

        excelExporter.export(response);
    }

    @GetMapping(value = "pdfReport",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> customerReport()throws IOException{
        List<Customer> customers=service.findAll();

        ByteArrayInputStream bis = GeneratePdfReport.customerReports(customers);

        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-Disposition","inline: filename=customersReport.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
    }



}
