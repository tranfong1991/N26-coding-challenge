package com.n26.challenge.models;

public class Transaction implements Comparable<Transaction>{
	private double amount;
	private long timestamp;
	
	public Transaction(){}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(Object obj) {
		Transaction t = (Transaction)obj;
		return this.timestamp == t.timestamp;
	}

	@Override
	public int compareTo(Transaction o) {
		double deltaAmount = this.amount - o.getAmount();
		if(deltaAmount < 0){
			return -1;
		}
		
		if(deltaAmount > 0){
			return 1;
		}
		
		long deltaTime = this.timestamp - o.getTimestamp();
		if(deltaTime < 0){
			return -1;
		}
		
		if(deltaTime > 0){
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(this.amount);
		sb.append(',');
		sb.append(this.timestamp);
		sb.append(')');
		return sb.toString();
	}
	
}
