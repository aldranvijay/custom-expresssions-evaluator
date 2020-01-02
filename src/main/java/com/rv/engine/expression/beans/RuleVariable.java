package com.rv.engine.expression.beans;

public class RuleVariable<V> {

	private String name;
	private V value;
	private Class<V> clsType;
	
	
	public RuleVariable(String name, V value, Class<V> clsType) {
		super();
		this.name = name;
		this.value = value;
		this.clsType = clsType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	public Class<V> getClsType() {
		return clsType;
	}
	public void setClsType(Class<V> clsType) {
		this.clsType = clsType;
	}
	
	
}
