package com.rv.ExpressionEvaluator;

import java.util.HashMap;
import java.util.Map;

import com.rv.engine.expression.beans.RuleVariable;
import com.rv.engine.expression.evaluators.EvaluateBasicMathExpression;


/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
    {
    	try {
        System.out.println( "Evaluation start ..." );
        RuleVariable<Double> var1 = new RuleVariable<Double>("LBA", 12000.0, null); 
        RuleVariable<Double> var2 = new RuleVariable<Double>("CA", 2000.0, null); 
        RuleVariable<Double> var3 = new RuleVariable<Double>("DA", 10.0, null); 
        Map<String, RuleVariable<?>> ruleVars = new HashMap<String, RuleVariable<?>>();
        ruleVars.put("LBA", var1);
        ruleVars.put("CA", var2);
        ruleVars.put("DA", var3);
        
        String expressionStr = "ADD(ADD(REF(LBA), REF(CA)), SUB(REF(CA),SUB(REF(CA),REF(DA))))";
        
        System.out.println( "End Result : "+new EvaluateBasicMathExpression().evaluateExpression(ruleVars, expressionStr).getResult() );
		
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
