package com.rv.engine.expression.dto;

public class ExpressionResult<R> {

	private String status;
	private R result;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public R getResult() {
		return result;
	}
	public void setResult(R result) {
		this.result = result;
	}
	
	
}
