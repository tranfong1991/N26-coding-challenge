package com.n26.challenge.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.n26.challenge.TestUtils;
import com.n26.challenge.models.Statistic;
import com.n26.challenge.models.Transaction;
import com.n26.challenge.services.N26Service;

@RunWith(SpringRunner.class)
public class N26ControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private N26Service challengeService;
	
	@InjectMocks
	private N26Controller n26Controller;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.standaloneSetup(n26Controller).build();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateTransaction_OldTransaction() throws Exception{
		Transaction transaction = new Transaction();
		transaction.setAmount(1.0);
		transaction.setTimestamp(1l);
		
		when(challengeService.createTransaction(any(Transaction.class))).thenReturn(false);
		mockMvc.perform(post("/transactions")
				.contentType(TestUtils.APPLICATION_JSON_UTF8)
				.content(TestUtils.convertObjectToJsonBytes(transaction)))
				.andExpect(status().isNoContent())
				.andExpect(content().string(""));
	}
	
	@Test
	public void testCreateTransaction_NewTransaction() throws Exception{
		Transaction transaction = new Transaction();
		transaction.setAmount(1.0);
		transaction.setTimestamp(2l);
		
		when(challengeService.createTransaction(any(Transaction.class))).thenReturn(true);
		mockMvc.perform(post("/transactions")
				.contentType(TestUtils.APPLICATION_JSON_UTF8)
				.content(TestUtils.convertObjectToJsonBytes(transaction)))
				.andExpect(status().isCreated())
				.andExpect(content().string(""));
	}
	
	@Test
	public void testGetStatistics() throws Exception{
		Statistic stats = new Statistic();
		stats.setSum(10.0);
		stats.setCount(5L);
		stats.setAvg(2.0);
		stats.setMax(7.0);
		stats.setMin(3.0);
		
		when(challengeService.getStatistics()).thenReturn(stats);
		mockMvc.perform(get("/statistics"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.avg", is(stats.getAvg())))
				.andExpect(jsonPath("$.sum", is(stats.getSum())))
				.andExpect(jsonPath("$.count", is((int)stats.getCount())))
				.andExpect(jsonPath("$.min", is(stats.getMin())))
				.andExpect(jsonPath("$.max", is(stats.getMax())));
	}
}
