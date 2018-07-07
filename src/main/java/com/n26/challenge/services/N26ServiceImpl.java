package com.n26.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.challenge.models.Statistic;
import com.n26.challenge.models.Transaction;
import com.n26.challenge.repositories.TransactionRepository;

@Service
public class N26ServiceImpl implements N26Service {
	
	@Autowired
	private TransactionRepository transactionRepo;

	@Override
	public boolean createTransaction(Transaction transaction) {
		return transactionRepo.addTransaction(transaction);
	}

	@Override
	public Statistic getStatistics() {
		return transactionRepo.getStats();
	}

}
