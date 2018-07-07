package com.n26.challenge.repositories;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

import com.n26.challenge.config.AppConfig;
import com.n26.challenge.models.Statistic;
import com.n26.challenge.models.Transaction;

//Singleton class
public class TransactionRepository {
	
	private class TimestampComparator implements Comparator<Transaction>{

		@Override
		public int compare(Transaction t1, Transaction t2) {
			if(t1.getTimestamp() < t2.getTimestamp()){
				return -1;
			} else if(t1.getTimestamp() > t2.getTimestamp()){
				return 1;
			}
			return 0;
		}
		
	}
	
	private static TransactionRepository instance;
	
	//Use BigDecimal to make addition and subtraction more precise
	private BigDecimal sum;
	private TreeSet<Transaction> transactionSet;
	
	//Maintains the order of transactions based on timestamp
	private PriorityQueue<Transaction> minQueue;
	
	private TransactionRepository(){
		this.sum = new BigDecimal(0);
		this.transactionSet = new TreeSet<>();
		this.minQueue = new PriorityQueue<>(new TimestampComparator());
	}
	
	public static TransactionRepository getInstance(){
		if(instance == null){
			instance = new TransactionRepository();
		}
		return instance;
	}
	
	public synchronized boolean addTransaction(Transaction transaction){		
		if(transaction.getTimestamp() < (System.currentTimeMillis() - AppConfig.MILISECONDS_BEFORE_NOW)){
			return false;
		}
		
		if(this.transactionSet.add(transaction)){
			this.sum = this.sum.add(new BigDecimal(transaction.getAmount()));
			this.minQueue.offer(transaction);
		}
				
		return true;
	}
	
	public Statistic getStats(){		
		Statistic stats = new Statistic();
		stats.setSum(this.sum.doubleValue());
		stats.setCount(this.transactionSet.size());
		stats.setAvg(this.sum.doubleValue() / this.transactionSet.size());
		stats.setMin(this.transactionSet.isEmpty() ? 0 : this.transactionSet.first().getAmount());
		stats.setMax(this.transactionSet.isEmpty() ? 0 : this.transactionSet.last().getAmount());
		
		return stats;
	}
	
	public boolean isEmpty(){
		return this.minQueue.isEmpty();
	}
	
	public Transaction getEldestTransaction(){
		return this.minQueue.peek();
	}
	
	public synchronized void removeEldestTransaction(){
		Transaction transaction = this.minQueue.poll();
		this.transactionSet.remove(transaction);
		this.sum = this.sum.subtract(new BigDecimal(transaction.getAmount()));
	}

}
