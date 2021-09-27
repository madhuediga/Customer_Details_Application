package com.cassandra.example.service;


import com.cassandra.example.model.Address;
import com.cassandra.example.model.Customer;

import com.cassandra.example.repositery.CustomerRepositery;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class CustomerService {

    @Autowired
    CustomerRepositery repo;
    @Autowired
    Environment env;

    public Integer createCustomer(Customer customer,int conversationId){
        List<Address> addressList = new ArrayList<>();
        if(customer.getPhoneNumber().length()==10){
            for(Address address : customer.getAddress()) {
                Address address1 = addAddress(address);
                addressList.add(address1);
                customer.setAddress(addressList);

            }
            customer.setConversationId(conversationId);
            customer.setBillingAccountNumber(ThreadLocalRandom.current().nextInt(999999999));
            repo.save(customer);
            return customer.getBillingAccountNumber();
        }else{
            return HttpStatus.BAD_REQUEST.value();
        }

    }

    public Address addAddress(Address add){
        String state = env.getProperty(add.getState());
        if(null!=state)
            add.setState(state);
        return add ;
    }


    public Customer updateCustomer(Customer customer,int billingAccountNumber){
        Optional<Customer> find= repo.findById(billingAccountNumber);
        Customer customer1=find.get();
        customer1.setAddress(customer.getAddress());
        customer1.setZip(customer.getZip());
        customer1.setEmailId(customer.getEmailId());
        return repo.save(customer1);
    }
    public Customer updateCustomerByPhone(Customer customer,String phoneNumber){
        Optional<Customer> find= repo.findByPhoneNumber(phoneNumber);
        Customer customer1=find.get();
        customer1.setAddress(customer.getAddress());
        customer1.setZip(customer.getZip());
        customer1.setEmailId(customer.getEmailId());
        return repo.save(customer1);
    }
    public boolean deleteByBillingNo(int billingAccountNumber){
        Optional<Customer> delete= repo.findById(billingAccountNumber);
        if (delete.isPresent()){
             repo.deleteById(billingAccountNumber);
             return true;
        }
        return false;
    }
    public boolean deleteByphoneNumber(String phoneNumber){
        Optional<Customer> find=repo.findByPhoneNumber(phoneNumber);
        if (find.isPresent()){
            repo.deleteById(find.get().getBillingAccountNumber());
            return true;
        }
        return false;
    }

    public Customer getBybillingAccNo(int billingAccountNumber){
        Optional<Customer> delete= repo.findById(billingAccountNumber);
        return delete.orElse(null);
    }

    public Optional<Customer> getByphoneNumber(String phno){
        return repo.findByPhoneNumber(phno);
    }
    public  List<Customer> findAll(){
        List<Customer> findAll=repo.findAll();
        return findAll;
    }


}
