package com.example.demo.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerDestination;

public class CustomerItemProcessor implements ItemProcessor<Customer, CustomerDestination> {
    private int i = 0;

    @Override
    public CustomerDestination process(Customer customer) {
        // Filter records based on the conditions
        if ("Yes".equals(customer.getCeCustomer()) && "to be migrate".equals(customer.getMigrationStatus())) {
            // Map Customer to CustomerDestination
            CustomerDestination customerDestination = new CustomerDestination(customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), customer.getGender(), customer.getContactNo(),
                    customer.getCountry(), customer.getDob());

            System.out.println(" called Api=============" + customer.getId());
            // Update migration status to "migrated" in the source table
            customer.setMigrationStatus("Migrated");
            System.out.println(" update Source table times=============" + ++i);
            return customerDestination;
        }
        return null; // Skip this record
    }
}
