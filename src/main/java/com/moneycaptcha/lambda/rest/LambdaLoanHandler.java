package com.moneycaptcha.lambda.rest;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.moneycaptcha.lambda.rest.model.LoanInput;
import com.moneycaptcha.lambda.rest.model.LoanOutput;

public class LambdaLoanHandler implements RequestHandler<LoanInput, LoanOutput>{

	@Override
	public LoanOutput handleRequest(LoanInput input, Context context) {
		
		final LoanOutput loanOutput = new LoanOutput();
		loanOutput.setFunctionName(context.getFunctionName());
		loanOutput.setUpdatedBalance(input.getPreviousBalance() - input.getInstallment());
		loanOutput.setMessage("Installment updated and latest balance is: " + loanOutput.getUpdatedBalance());
		return loanOutput;
	}

	
}
