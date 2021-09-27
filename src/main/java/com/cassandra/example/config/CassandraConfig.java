package com.cassandra.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "com.cassandra.example.repositery")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name: demo}")
    private String keyspace;

    @Value("${spring.data.cassandra.contact-points: 127.0.0.1}")
    private String contactPoint;

    @Value("${spring.data.cassandra.port: 9042}")
    private int port;

    @Value("${com.cassandra.example}")
    private String basePackage;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    public String getContactPoints() {
        return contactPoint;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }


    @Override
    public String[] getEntityBasePackages() {
        return new String[] {"com.cassandra.example.model"};
    }



}