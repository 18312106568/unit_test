/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MRB
 */
public class ConverUtil {

    private static final String SET = "set";

    private static final String GET = "get";

    /**
     * Map转换为目标对象
     *
     * @param <T>
     * @param paramMap
     * @param clazz
     * @return
     */
    public static <T> T converToTarget(Map<String, String> paramMap, Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            Field fields[] = clazz.getFields();
            for (Field field : fields) {
                Object o = null;
                Method method = clazz.getDeclaredMethod(toSetName(field.getName()), field.getType());
                if(StringUtils.isEmpty(paramMap.get(field.getName()))){
                    o = null;
                }else if (field.getType().equals(Integer.class)) {
                    o = Integer.parseInt(paramMap.get(field.getName()));
                } else if (field.getType().equals(Date.class)) {
                    o = new Date(Integer.parseInt(paramMap.get(field.getName())));
                } else if (field.getType().equals(Float.class)) {
                    o = Float.parseFloat(paramMap.get(field.getName()));
                } else if (field.getType().equals(Double.class)) {
                    o = Double.parseDouble(paramMap.get(field.getName()));
                } else if (field.getType().equals(Long.class)) {
                    o = Long.parseLong(paramMap.get(field.getName()));
                } else if (field.getType().equals(Short.class)) {
                    o = Short.parseShort(paramMap.get(field.getName()));
                } else if (field.getType().equals(Boolean.class)) {
                    o = Boolean.parseBoolean(paramMap.get(field.getName()));
                } else if (Enum.class.equals(field.getType().getSuperclass())) {
                    o = Enum.valueOf((Class) field.getType(), paramMap.get(field.getName()));
                } else {
                    o = null;
                }
                method.invoke(instance, o);
            }
            return instance;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 分割字符串成Map
     *
     * @param data
     * @param seporator
     * @return
     */
    public static Map converToMap(String data, String seporator) {
        Map<String, String> result = new HashMap();
        String[] dataArr = data.split(seporator);
        for (int i = 0; i < dataArr.length; i++) {
            String[] entry = dataArr[i].split("\\=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    /**
     * 分割字符串成Map
     *
     * @param data
     * @param seporator
     * @return
     */
    public static Map<String,String> converToMap(String data, String seporator,String subSeporator) {
        Map<String, String> result = new HashMap();
        String[] dataArr = data.split(seporator);
        for (int i = 0; i < dataArr.length; i++) {
            String[] entry = dataArr[i].split(subSeporator,2);
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    /**
     * Map拼接成字符串
     *
     * @param dataMap
     * @param seporator
     * @return
     */
    public static String converToStr(Map<String, String> dataMap, String seporator) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(seporator);
        }
        return sb.toString();
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 获取set方法名
     *
     * @param fieldName
     * @return
     */
    private static String toSetName(String fieldName) {
        return SET + upperCase(fieldName);
    }

    /**
     * 获取get方法名
     *
     * @param fieldName
     * @return
     */
    private static String toGetName(String fieldName) {
        return GET + upperCase(fieldName);
    }

    /**
     * 峰驼式转匈牙利命名
     *
     * @param cameString
     * @return
     */
    public static String changeCameToHung(String cameString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cameString.length(); i++) {
            int c = cameString.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                sb.append("_").append((char) (c + 32));
                continue;
            }
            sb.append((char) c);
        }
        return sb.toString();
    }


    /**
     * 短语转峰驼式命名
     */
    public static String phraseToChangCame(String phrase){
        boolean flag = false;
        phrase = phrase.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<phrase.length();i++){
            int c = phrase.charAt(i);
            if(c==' '){
                flag = true;
                continue;
            }
            if(flag){
                sb.append((char)(c-32));
                flag=false;
                continue;
            }
            sb.append((char)c);
        }
        return sb.toString();
    }

}
