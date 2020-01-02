package com.rv.engine.expression.evaluators;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.rv.engine.expression.beans.Operator;
import com.rv.engine.expression.beans.RuleVariable;
import com.rv.engine.expression.dto.ExpressionResult;

public class EvaluateBasicMathExpression<V, R> {

	Map<String, V> ruleVars;
	Map<String, Operator<?>> supportedOperators = new HashMap<String, Operator<?>>();
	
	public EvaluateBasicMathExpression(){
		Operator<Double> addOpr = new Operator<Double>();
		addOpr.setName("add");
		addOpr.setNoOfParamsAllowed(2);
		supportedOperators.put("ADD", addOpr);
		
		Operator<Double> subOpr = new Operator<Double>();
		subOpr.setName("substract");
		subOpr.setNoOfParamsAllowed(2);
		supportedOperators.put("SUB", subOpr);
		
		Operator<Double> mulOpr = new Operator<Double>();
		mulOpr.setName("multiply");
		mulOpr.setNoOfParamsAllowed(2);
		supportedOperators.put("MUL", mulOpr);
		
		Operator<Double> divOpr = new Operator<Double>();
		divOpr.setName("division");
		divOpr.setNoOfParamsAllowed(2);
		supportedOperators.put("DIV", divOpr);
		
		Operator<?> refOpr = new Operator();
		refOpr.setName("refVal");
		refOpr.setNoOfParamsAllowed(1);
		supportedOperators.put("REF", refOpr);
		
		Operator<?> numOpr = new Operator();
		numOpr.setName("num");
		numOpr.setNoOfParamsAllowed(1);
		supportedOperators.put("NUM", numOpr);
		
		Operator<?> strOpr = new Operator();
		strOpr.setName("text");
		strOpr.setNoOfParamsAllowed(1);
		supportedOperators.put("TXT", strOpr);
	}
	
	public ExpressionResult evaluateExpression(Map<String, V> ruleVars, String expression) throws Exception {
		System.out.println( "Expression evaluation for : " +expression);
		this.ruleVars = ruleVars;
		Stack<Operator<?>> ops = new Stack<Operator<?>>();
		ExpressionResult<Double> result = new ExpressionResult<Double>();
		result.setStatus("fail");
		Operator<?> opr = null;
		String[] oprExpressionArr = expression.split("\\(", 10);
		
		for(String expressionStrPart : oprExpressionArr) {
			if(expressionStrPart.indexOf(",") > -1) {
				String leftStr = expressionStrPart.substring(0, (expressionStrPart.indexOf(",") > expressionStrPart.indexOf(")")) ? expressionStrPart.indexOf(")") : expressionStrPart.indexOf(","));
				leftStr = leftStr.trim();
				ops = this.stackify(leftStr, "LEFT", ops);
				
				String rightStr = expressionStrPart.substring(expressionStrPart.indexOf(",")+1, expressionStrPart.length());
				rightStr = rightStr.trim();
				ops = this.stackify(rightStr, "RIGHT", ops);
				
			}else if(expressionStrPart.indexOf(")") > -1){
				expressionStrPart = expressionStrPart.substring(0, (expressionStrPart.length() > expressionStrPart.indexOf(")")) ? expressionStrPart.indexOf(")") : expressionStrPart.length());
				expressionStrPart = expressionStrPart.trim();
				ops = this.stackify(expressionStrPart, "LEFT", ops);
				
			}else {
				expressionStrPart = expressionStrPart.substring(0, expressionStrPart.length() );
				expressionStrPart = expressionStrPart.trim();
				ops = this.stackify(expressionStrPart, "LEFT", ops);
				
			}
		}
		
		Double output = (Double)this.executeStack(ops);
		System.out.println("Exiting from evaluation with output : "+output);
		result.setStatus("success");
		result.setResult(output);
		return result;
	}
	
	private Stack<Operator<?>> stackify(String expression, String alignFlag, Stack<Operator<?>> ops) throws Exception {
		Operator<?> opr = null;
		if(supportedOperators.containsKey(expression)) { 
			opr = supportedOperators.get(expression);
			opr = opr.clone();
			opr.setParamAlignType(alignFlag);
			ops.push(opr);
		}else {
			opr = ops.pop();
			if(opr.getName() == "refVal" || opr.getName() == "text")
			{
				Operator<String> oprTxt = new Operator<String>();
				oprTxt.setName(opr.getName());
				oprTxt.setValue(expression); 
				oprTxt.setNoOfParamsAllowed(1);
				oprTxt.setParamAlignType(opr.getParamAlignType()); 
				ops.push(oprTxt);
			}else if(opr.getName() == "num") {
				Operator<Double> oprDouble = new Operator<Double>();
				oprDouble.setName(opr.getName());
				oprDouble.setValue(Double.valueOf(expression)); 
				oprDouble.setNoOfParamsAllowed(1);
				oprDouble.setParamAlignType(opr.getParamAlignType());
				ops.push(oprDouble);
			}else {
				ops.push(opr);
			}
		}
		
		return ops;
	}
	
	private Object executeStack(Stack<Operator<?>> ops) {
		
		Stack<Object> leftOps = new Stack<Object>();
		Stack<Object> rightOps = new Stack<Object>();
		Object oprationResult = null;
		while(!ops.empty()) {
			Operator<?> oprRet = ops.pop();
			
			switch(oprRet.getName()) {
				case "add":
					oprationResult = this.add((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "substract":
					oprationResult = this.substract((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "division":
					oprationResult = this.division((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "multiply":
					oprationResult = this.multiply((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "refVal":
					oprationResult = this.refVal((String)oprRet.getValue());
					break;
				case "num":
					oprationResult = this.num((Double)oprRet.getValue());
					break;
				case "text":
					oprationResult = this.text((String)oprRet.getValue());
					break;
				default:
					oprationResult = null;
					System.out.println("Default @ToDo operation"); 
			}
			
			if(oprRet.getParamAlignType() == "LEFT") {
				leftOps.push(oprationResult);
			}else if(oprRet.getParamAlignType() == "RIGHT") {
				rightOps.push(oprationResult);
			}
		};
		return leftOps.pop();
	}
	
    private Object execute(Stack<Operator<?>> ops) {
		
		Stack<Object> leftOps = new Stack<Object>();
		Stack<Object> rightOps = new Stack<Object>();
		Object oprationResult = null;
		while(!ops.empty()) {
			Operator<?> oprRet = ops.pop();
//			try {
//				this.getClass().getMethod("",int.class);
//			} catch (NoSuchMethodException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			switch(oprRet.getName()) {
				case "add":
					oprationResult = this.add((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "substract":
					oprationResult = this.substract((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "division":
					oprationResult = this.division((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "multiply":
					oprationResult = this.multiply((Double)leftOps.pop(), (Double)rightOps.pop() );
					break;
				case "refVal":
					oprationResult = this.refVal((String)oprRet.getValue());
					break;
				case "num":
					oprationResult = this.num((Double)oprRet.getValue());
					break;
				case "text":
					oprationResult = this.text((String)oprRet.getValue());
					break;
				default:
					oprationResult = null;
					System.out.println("Default @ToDo operation"); 
			}
			
			if(oprRet.getParamAlignType() == "LEFT") {
				leftOps.push(oprationResult);
			}else if(oprRet.getParamAlignType() == "RIGHT") {
				rightOps.push(oprationResult);
			}
		};
		return leftOps.pop();
	}
	
	public Operator<?> getOperator(String expression){
		Operator<?> opr = null;
		String[] oprArr = expression.split("(");
		
		return opr;
	}
	
	private Double add(Double a, Double b) {
		return a + b;
	}
	
	private Double substract(Double a, Double b) {
		return a - b;
	}
	
	private Double multiply(Double a, Double b) {
		return a * b;
	}
	
	private Double division(Double a, Double b) {
		return a / b;
	}
	
	private Object refVal(String lebel) {
		RuleVariable var = (RuleVariable) ruleVars.get(lebel);
		return var.getValue(); 
	}
	
	private Double num(Double value) {
		return value; 
	}
	
	private String text(String value) {
		return value; 
	}
	
}
