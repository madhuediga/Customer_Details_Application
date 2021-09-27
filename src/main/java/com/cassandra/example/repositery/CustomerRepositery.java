package com.cassandra.example.repositery;

import com.cassandra.example.model.Customer;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepositery extends CassandraRepository<Customer, Integer> {


    @AllowFiltering
    Optional<Customer> findByPhoneNumber(final String phoneNumber);


}
