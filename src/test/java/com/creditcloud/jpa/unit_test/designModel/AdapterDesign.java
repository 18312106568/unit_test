package com.creditcloud.jpa.unit_test.designModel;

public class AdapterDesign {

    // 类适配器: 基于继承
    public interface ITarget {
        void f1();

        void f2();

        void fc();
    }

    public class Adaptee {
        public void fa() { //...
        }

        public void fb() { //...
        }

        public void fc() { //...
        }
    }

    public class Adaptor extends Adaptee implements ITarget {
        public void f1() {
            super.fa();
        }

        public void f2() {
            //...重新实现f2()...
        }

        // 这里fc()不需要实现，直接继承自Adaptee，这是跟对象适配器最大的不同点
    }

    // 对象适配器：基于组合
    public class Adaptor2 implements ITarget {
        private Adaptee adaptee;

        public Adaptor2(Adaptee adaptee) {
            this.adaptee = adaptee;
        }

        public void f1() {
            adaptee.fa(); //委托给Adaptee
        }

        public void f2() {
            //...重新实现f2()...
        }

        public void fc() {
            adaptee.fc();
        }
    }


}

