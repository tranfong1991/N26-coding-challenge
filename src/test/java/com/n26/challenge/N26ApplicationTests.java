package com.n26.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challenge.controllers.N26Controller;

@RunWith(SpringRunner.class)
@SpringBootTest
public class N26ApplicationTests {

	@Autowired
	private N26Controller n26Controller;
	
	@Test
	public void contextLoads() {
		assertThat(n26Controller).isNotNull();
	}

}
