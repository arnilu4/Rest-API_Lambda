package com.moneycaptcha.lambda.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moneycaptcha.lambda.rest.model.LoanInput;
import com.moneycaptcha.lambda.rest.model.LoanOutput;

@RestController
public class LoanController {

	@PostMapping("/loan")
	@RequestMapping(method = RequestMethod.POST)
	  public ResponseEntity<?> addInstallment(@Valid @RequestBody LoanInput loanInput) {
		LoanOutput loanOutput = new LoanOutput();
		loanOutput.setUpdatedBalance(loanInput.getPreviousBalance()+ loanInput.getInstallment());
		loanOutput.setMessage("Installment updated and latest balance is: " + loanOutput.getUpdatedBalance());
	    return new ResponseEntity<>(loanOutput, HttpStatus.ACCEPTED);
	  }
}
