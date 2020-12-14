package com.devsuperior.hrpayroll.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;

@Service
public class PaymentService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${hr-worker.host}")
	private String workerHost;
	
	public Payment getPayment(long workerId, int days) {		
		Worker worker = restTemplate.getForObject(this.workerHost + "/workers/" + workerId, Worker.class);
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}
	
}
