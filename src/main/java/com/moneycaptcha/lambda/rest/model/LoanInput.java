package com.moneycaptcha.lambda.rest.model;

public class LoanInput {

	private float previousBalance;
	private float installment;
	private boolean validUpdate;

	public float getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(float previousBalance) {
		this.previousBalance = previousBalance;
	}

	public float getInstallment() {
		return installment;
	}

	public void setInstallment(float installment) {
		this.installment = installment;
	}

	public boolean isValidUpdate() {
		return validUpdate;
	}

	public void setValidUpdate(boolean validUpdate) {
		this.validUpdate = validUpdate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private String action;
}
