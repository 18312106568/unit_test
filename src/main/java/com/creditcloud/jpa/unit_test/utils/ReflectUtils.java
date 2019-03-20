package com.creditcloud.jpa.unit_test.utils;

import java.lang.reflect.Field;

public class ReflectUtils {

    public static Field getField (Class clazz,String fieldName){
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.getName().equals(fieldName)){
                field.setAccessible(true);
                return field;
            }
            continue;
        }
        throw new RuntimeException(String.format("can not find the field:%s in class:%s",fieldName,clazz.getName()));
    }
}
