package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Transactional
	@Modifying
	@Query("UPDATE Customer c SET c.migrationStatus = 'migrated' WHERE c.id = :customerId")
	int updateStatusById(int customerId);
}
