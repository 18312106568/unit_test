package com.creditcloud.jpa.unit_test.designModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterpreterDesign {

    public interface Expression {
        boolean interpret(Map<String, Long> stats);
    }
    public class GreaterExpression implements Expression {
        private String key;
        private long value;
        public GreaterExpression(String strExpression) {
            String[] elements = strExpression.trim().split("\\s+");
            if (elements.length != 3 || !elements[1].trim().equals(">")) {
                throw new RuntimeException("Expression is invalid: " + strExpression);
            }
            this.key = elements[0].trim();
            this.value = Long.parseLong(elements[2].trim());
        }
        public GreaterExpression(String key, long value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public boolean interpret(Map<String, Long> stats) {
            if (!stats.containsKey(key)) {
                return false;
            }
            long statValue = stats.get(key);
            return statValue > value;
        }
    }

    public class LessExpression implements Expression {
        private String key;
        private long value;
        public LessExpression(String strExpression) {
            String[] elements = strExpression.trim().split("\\s+");
            if (elements.length != 3 || !elements[1].trim().equals("<")) {
                throw new RuntimeException("Expression is invalid: " + strExpression);
            }
            this.key = elements[0].trim();
            this.value = Long.parseLong(elements[2].trim());
        }
        public LessExpression(String key, long value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public boolean interpret(Map<String, Long> stats) {
            if (!stats.containsKey(key)) {
                return false;
            }
            long statValue = stats.get(key);
            return statValue < value;
        }
    }

    public class EqualExpression implements Expression {
        private String key;
        private long value;
        public EqualExpression(String strExpression) {
            String[] elements = strExpression.trim().split("\\s+");
            if (elements.length != 3 || !elements[1].trim().equals("==")) {
                throw new RuntimeException("Expression is invalid: " + strExpression);
            }
            this.key = elements[0].trim();
            this.value = Long.parseLong(elements[2].trim());
        }
        public EqualExpression(String key, long value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public boolean interpret(Map<String, Long> stats) {
            if (!stats.containsKey(key)) {
                return false;
            }
            long statValue = stats.get(key);
            return statValue == value;
        }
    }

    public class AndExpression implements Expression {
        private List<Expression> expressions = new ArrayList<>();
        public AndExpression(String strAndExpression) {
            String[] strExpressions = strAndExpression.split("&&");
            for (String strExpr : strExpressions) {
                if (strExpr.contains(">")) {
                    expressions.add(new GreaterExpression(strExpr));
                } else if (strExpr.contains("<")) {
                    expressions.add(new LessExpression(strExpr));
                } else if (strExpr.contains("==")) {
                    expressions.add(new EqualExpression(strExpr));
                } else {
                    throw new RuntimeException("Expression is invalid: " + strAndExpression);
                }
            }
        }
        public AndExpression(List<Expression> expressions) {
            this.expressions.addAll(expressions);
        }
        @Override
        public boolean interpret(Map<String, Long> stats) {
            for (Expression expr : expressions) {
                if (!expr.interpret(stats)) {
                    return false;
                }
            }
            return true;
        }
    }
    public class OrExpression implements Expression {
        private List<Expression> expressions = new ArrayList<>();
        public OrExpression(String strOrExpression) {
            String[] andExpressions = strOrExpression.split("\\|\\|");
            for (String andExpr : andExpressions) {
                expressions.add(new AndExpression(andExpr));
            }
        }
        public OrExpression(List<Expression> expressions) {
            this.expressions.addAll(expressions);
        }
        @Override
        public boolean interpret(Map<String, Long> stats) {
            for (Expression expr : expressions) {
                if (expr.interpret(stats)) {
                    return true;
                }
            }
            return false;
        }
    }
    public class AlertRuleInterpreter {
        private Expression expression;
        public AlertRuleInterpreter(String ruleExpression) {
            this.expression = new OrExpression(ruleExpression);
        }
        public boolean interpret(Map<String, Long> stats) {
            return expression.interpret(stats);
        }
    }

    @Test
    public  void domain() {
        String rule = "key1 > 100 && key2 < 30 || key3 < 100 || key4 == 88";
        AlertRuleInterpreter interpreter = new AlertRuleInterpreter(rule);
        Map<String, Long> stats = new HashMap<>();
        stats.put("key1", 101l);
        stats.put("key3", 121l);
        stats.put("key4", 87L);
        boolean alert = interpreter.interpret(stats);
        System.out.println(alert);
    }

}
