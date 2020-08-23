package com.creditcloud.jpa.unit_test.designModel;

public class TemplateMethodDesign {
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


    public interface ICallback {
        void methodToCallback();
    }
    public class BClass {
        public void process(ICallback callback) {
            //...
            callback.methodToCallback();
            //...
        }
    }
    public class AClass {
        public  void doBack() {
            BClass b = new BClass();
            b.process(new ICallback() { //回调对象
                @Override
                public void methodToCallback() {
                    System.out.println("Call back me.");
                }
            });
        }
    }
}
