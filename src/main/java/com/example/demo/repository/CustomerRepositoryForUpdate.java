package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;

@Repository
public class CustomerRepositoryForUpdate {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public String getUUID() {
		String uuid = jdbcTemplate.queryForObject("select count(*) from CUSTOMERS_INFO", String.class);
		return uuid;
	}

	public int updateMigrationStatus(int customerId) {
		System.out.println("customerId========================" + customerId);
		String uuid = getUUID();
		System.out.println("uuid +=========" + uuid);
		return jdbcTemplate.update("UPDATE CUSTOMERS_INFO SET migration_status = 'migrated' WHERE CUSTOMER_ID = ?",
				customerId);
	}

	public List<Customer> getAllCustomers() {
		return jdbcTemplate.query("SELECT * FROM CUSTOMERS_INFO", (rs, rowNum) -> {
			Customer customer = new Customer();
			customer.setId(rs.getInt("CUSTOMER_ID"));
			customer.setFirstName(rs.getString("FIRST_NAME"));
			customer.setLastName(rs.getString("LAST_NAME"));
			customer.setEmail(rs.getString("EMAIL"));
			customer.setGender(rs.getString("GENDER"));
			customer.setContactNo(rs.getString("CONTACT"));
			customer.setCountry(rs.getString("COUNTRY"));
			customer.setDob(rs.getString("DOB"));
//			customer.setMigrationStatus(rs.getString("migrations_status"));
//			customer.setCeCustomer(rs.getString("ce_customer"));
			return customer;
		});
	}

}
