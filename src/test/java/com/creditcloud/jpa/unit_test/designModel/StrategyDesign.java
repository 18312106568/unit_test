package com.creditcloud.jpa.unit_test.designModel;

import java.util.HashMap;
import java.util.Map;

public class StrategyDesign {

    public interface Strategy {
        void algorithmInterface();
    }
    public static class ConcreteStrategyA implements Strategy {
        @Override
        public void  algorithmInterface() {
            //具体的算法...
        }
    }
    public static class ConcreteStrategyB implements Strategy {
        @Override
        public void  algorithmInterface() {
            //具体的算法...
        }
    }

    public static class StrategyFactory {
        private static final Map<String, Strategy> strategies = new HashMap<>();
        static {
            strategies.put("A", new ConcreteStrategyA());
            strategies.put("B", new ConcreteStrategyB());
        }
        public static Strategy getStrategy(String type) {
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("type should not be empty.");
            }
            return strategies.get(type);
        }
    }
}
