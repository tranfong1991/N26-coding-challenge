package com.n26.challenge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.challenge.models.Statistic;
import com.n26.challenge.models.Transaction;
import com.n26.challenge.services.N26Service;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
public class N26Controller {
	
	@Autowired
	private N26Service n26Service;
	
	@ApiOperation(value = "Insert a transaction.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully inserted a transaction."),
			@ApiResponse(code = 204, message = "Transaction is older than 60 seconds.")
	})
	@RequestMapping(value = "transactions", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> createTransaction(@RequestBody Transaction transaction){
		boolean success = n26Service.createTransaction(transaction);
		return success ? new ResponseEntity<>(HttpStatus.CREATED) : ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Get statistics of the last 60 seconds.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved statistics.")
	})
	@RequestMapping(value = "statistics", method = RequestMethod.GET)
	public ResponseEntity<Statistic> getStatistics(){
		return ResponseEntity.ok(n26Service.getStatistics());
	}
	
}
