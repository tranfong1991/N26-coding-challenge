package com.n26.challenge.services;

import com.n26.challenge.models.Statistic;
import com.n26.challenge.models.Transaction;

public interface N26Service {
	boolean createTransaction(Transaction transaction);
	Statistic getStatistics();
}
