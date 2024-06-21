package com.example.demo.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerDestination;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private EntityManagerFactory entityManagerFactory;

	public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			EntityManagerFactory entityManagerFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.entityManagerFactory = entityManagerFactory;
	}

	@Bean
	public Job migrationJob() {
		return jobBuilderFactory.get("migrationJob").incrementer(new RunIdIncrementer()).start(migrationStep()).build();
	}

	@Bean
	public Step migrationStep() {
		return stepBuilderFactory.get("migrationStep").<Customer, CustomerDestination>chunk(10).reader(itemReader())
				.processor(new CustomerItemProcessor()).writer(itemWriter()).build();
	}

	@Bean
	public JpaPagingItemReader<Customer> itemReader() {
		JpaPagingItemReader<Customer> reader = new JpaPagingItemReader<>();
		reader.setQueryString("SELECT c FROM Customer c");
		reader.setEntityManagerFactory(entityManagerFactory);
		return reader;
	}

	@Bean
	public JpaItemWriter<CustomerDestination> itemWriter() {
		JpaItemWriter<CustomerDestination> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}
}
