package com.n26.challenge.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.n26.challenge.models.Transaction;
import com.n26.challenge.repositories.TransactionRepository;

@Component
public class HouseKeepingScheduler {
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Scheduled(fixedRate = 1000)
	public void houseKeep(){		
		while(!transactionRepo.isEmpty()){
			Transaction eldestTransaction = transactionRepo.getEldestTransaction();
			if(eldestTransaction.getTimestamp() >= (System.currentTimeMillis() - 60000)){
				break;
			}
			
			transactionRepo.removeEldestTransaction();
		}
	}
	
}
