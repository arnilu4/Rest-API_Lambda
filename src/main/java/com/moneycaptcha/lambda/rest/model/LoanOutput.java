package com.moneycaptcha.lambda.rest.model;

public class LoanOutput {

	private String message;
	private float updatedBalance;
	private String functionName;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public float getUpdatedBalance() {
		return updatedBalance;
	}

	public void setUpdatedBalance(float updatedBalance) {
		this.updatedBalance = updatedBalance;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

}
