package com.rv.engine.expression.beans;

public class Param<P> {

	private String lebel;
	private P paramType;
	
	public String getLebel() {
		return lebel;
	}
	public void setLebel(String lebel) {
		this.lebel = lebel;
	}
	public P getParamType() {
		return paramType;
	}
	public void setParamType(P paramType) {
		this.paramType = paramType;
	}
	
	
	
}
