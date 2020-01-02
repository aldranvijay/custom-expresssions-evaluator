First download and run the project.

Then, use below snippet to test as per your convenience:

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
