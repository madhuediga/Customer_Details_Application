package com.cassandra.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Setter
@Getter
@ToString
@Table("customers")
public class Customer {

	@PrimaryKey
	private int billingAccountNumber;

	private String firstName;

	private String lastName;


	@Pattern(regexp = "(^$|[0-9]{10})", message = "Phone Number must be 10 digit number")
	private String phoneNumber;

	@Min(value = 11111)
	@Max(value = 99999)
	@Column("zip")
	private int zip;

	@Pattern(regexp = "^(.+)@(.+)$", message = "Email must be @ symbol")
	private String emailId;

	private int conversationId;

	@Column("address")
	private List<Address> address;
}

