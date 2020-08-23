package com.creditcloud.jpa.unit_test.designModel;

import java.util.ArrayList;
import java.util.List;

//高层模块（high-level modules）不要依赖低层模块（low-level）。高层模块和低层模块应该通过抽象（abstractions）来互相依赖。除此之外，抽象（abstractions）不要依赖具体实现细节（details），具体实现细节（details）依赖抽象（abstractions）。
//控制反转:通过框架来实现“控制反转”的例子。框架提供了一个可扩展的代码骨架，用来组装对象、管理整个执行流程
//依赖注入：不通过 new() 的方式在类内部创建依赖类对象，而是将依赖的类对象在外部创建好之后，通过构造函数、函数参数等方式传递（或注入）给类使用
public class DependencyInversionPrinciple {

    //控制反转
    public  abstract class TestCase {
        public void run() {
            if (doTest()) {
                System.out.println("Test succeed.");
            } else {
                System.out.println("Test failed.");
            }
        }

        public abstract boolean doTest();
    }
    public  class JunitApplication {
        private  final List<TestCase> testCases = new ArrayList<>();

        public  void register(TestCase testCase) {
            testCases.add(testCase);
        }

        public void excute() {
            for(TestCase testCase :testCases){
                testCase.run();
            }
        }
    }

}
