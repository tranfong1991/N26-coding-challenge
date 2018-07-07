package com.n26.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.n26.challenge.repositories.TransactionRepository;

@Configuration
public class AppConfig {

	@Bean
	public TransactionRepository getTransactionRepo(){
		return TransactionRepository.getInstance();
	}
	
}
