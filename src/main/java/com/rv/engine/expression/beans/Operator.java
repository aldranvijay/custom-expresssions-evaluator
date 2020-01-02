package com.rv.engine.expression.beans;

import java.util.List;

public class Operator<T> implements Cloneable{

	private String name;            //Operator Name
	private Class<T> returnType;
	private String paramAlignType;  //LEFT/RIGHT/MAIN
	private int noOfParamsAllowed;  //Max Param Allowed
	private T value;
	private List<Param> params;
	
	public Operator<T> clone() throws CloneNotSupportedException 
	{ 
	   return (Operator<T>) super.clone(); 
	} 
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<T> getReturnType() {
		return returnType;
	}
	public void setReturnType(Class<T> returnType) {
		this.returnType = returnType;
	}
	public String getParamAlignType() {
		return paramAlignType;
	}
	public void setParamAlignType(String paramAlignType) {
		this.paramAlignType = paramAlignType;
	}
	public int getNoOfParamsAllowed() {
		return noOfParamsAllowed;
	}
	public void setNoOfParamsAllowed(int noOfParamsAllowed) {
		this.noOfParamsAllowed = noOfParamsAllowed;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}
	
	
	
	
}
