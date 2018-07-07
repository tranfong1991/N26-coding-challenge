package com.n26.challenge.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challenge.models.Statistic;
import com.n26.challenge.models.Transaction;
import com.n26.challenge.repositories.TransactionRepository;

@RunWith(SpringRunner.class)
public class N26ServiceImplTest {
	
	private static final double EPSILON = 0.001;
	
	@Mock
	private TransactionRepository transactionRepo;

	@InjectMocks
	private N26ServiceImpl n26Service;
	
	@Test
	public void testCreateTransaction(){
		when(transactionRepo.addTransaction(any(Transaction.class))).thenReturn(true);
		assertEquals(n26Service.createTransaction(new Transaction()), true);
	}
	
	@Test
	public void testGetStatistics(){
		Statistic expected = new Statistic();
		expected.setSum(10.0);
		expected.setCount(5L);
		expected.setAvg(2.0);
		expected.setMax(7.0);
		expected.setMin(3.0);
		
		when(transactionRepo.getStats()).thenReturn(expected);
		
		Statistic result = n26Service.getStatistics();
		assertEquals(result.getSum(), expected.getSum(), EPSILON);
		assertEquals(result.getAvg(), expected.getAvg(), EPSILON);
		assertEquals(result.getCount(), expected.getCount());
		assertEquals(result.getMin(), expected.getMin(), EPSILON);
		assertEquals(result.getMax(), expected.getMax(), EPSILON);
	}
	
}
