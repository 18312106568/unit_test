package com.creditcloud.jpa.unit_test.GoF;

public class TemplateDesignMode {

    public abstract class AbstractClass {
        public final void templateMethod() {
            //...
            method1();
            //...
            method2();
            //...
        }

        protected abstract void method1();
        protected abstract void method2();
    }
    public class ConcreteClass1 extends AbstractClass {
        @Override
        protected void method1() {
            //...
        }

        @Override
        protected void method2() {
            //...
        }
    }
    public class ConcreteClass2 extends AbstractClass {
        @Override
        protected void method1() {
            //...
        }

        @Override
        protected void method2() {
            //...
        }
    }
}
