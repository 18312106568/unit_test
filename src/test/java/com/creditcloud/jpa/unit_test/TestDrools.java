package com.creditcloud.jpa.unit_test;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.io.impl.ClassPathResource;
import org.junit.Test;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestDrools {

    @Data
    @AllArgsConstructor
    static public class Stu{
        private String name;

        private int age;

        private String gender;
    }

    @Test
    public void testClassSourcePath() throws IOException {
        System.out.println(new org.springframework.core.io.ClassPathResource("drl/Stu.drl").getURL());
    }

    @Test
    public void testStuRule(){
        //创建规则构建者
        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(new ClassPathResource("drl/Stu.drl"), ResourceType.DRL);

        //获取规则包
        Collection<KiePackage> collection = kb.getKnowledgePackages();
        InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addPackages(collection);

        //获取规则会话
        KieSession statefulSession = knowledgeBase.newKieSession();
        List<Stu> list = getStus();

        //插入规则
        for (Stu stu : list) {
            statefulSession.insert(stu);
        }
        statefulSession.fireAllRules();
        statefulSession.dispose();
    }


    public static List<Stu> getStus() {
        List<Stu> stus = new ArrayList<>();
        stus.add(new Stu("张三", 16, "male"));
        stus.add(new Stu("huhu", 18, "male"));
        stus.add(new Stu("王五", 32, "male"));
        stus.add(new Stu("张红", 23, "female"));
        stus.add(new Stu("李四", 35, "male"));
        stus.add(new Stu("张小雅", 31, "female"));
        return stus;
    }

    public static void alertStu(){
        System.out.println("拥有未成年的孩子");
    }
}
