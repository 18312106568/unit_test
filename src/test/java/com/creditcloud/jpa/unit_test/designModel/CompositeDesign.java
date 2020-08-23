package com.creditcloud.jpa.unit_test.designModel;

import java.util.ArrayList;
import java.util.List;

public class CompositeDesign {
    public abstract class HumanResource {
        protected long id;
        protected double salary;
        public HumanResource(long id) {
            this.id = id;
        }
        public long getId() {
            return id;
        }
        public abstract double calculateSalary();
    }
    public class Employee extends HumanResource {
        public Employee(long id, double salary) {
            super(id);
            this.salary = salary;
        }
        @Override
        public double calculateSalary() {
            return salary;
        }
    }
    public class Department extends HumanResource {
        private List<HumanResource> subNodes = new ArrayList<>();
        public Department(long id) {
            super(id);
        }
        @Override
        public double calculateSalary() {
            double totalSalary = 0;
            for (HumanResource hr : subNodes) {
                totalSalary += hr.calculateSalary();
            }
            this.salary = totalSalary;
            return totalSalary;
        }
        public void addSubNode(HumanResource hr) {
            subNodes.add(hr);
        }
    }
}
