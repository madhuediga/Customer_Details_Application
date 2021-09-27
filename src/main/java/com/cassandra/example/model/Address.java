package com.cassandra.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@UserDefinedType("address")
@ConfigurationProperties("address")
public class Address {

        private String addressLine1;

        private String addressLine2;

        @Column("city")
        private String city;

        @Size(min=2,max = 2,message = "sate must be in 2 characteristics")
        @Column("state")
        private String state;


    }
