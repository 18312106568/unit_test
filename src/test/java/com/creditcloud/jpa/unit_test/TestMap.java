/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 *
 * @author MRB
 */
public class TestMap {

    @Test
    public void testHashMap() {
        HashMap<String, Integer> map = new HashMap();
        String str1 = "Aa";
        String str2 = "BB";
        String str3 = "C#";
        String str4 = "D" + (char) 4;
        String str5 = "@" + (char) 128;
        String str6 = "" + (char) 63 + (char) 159;
        String str7 = "" + (char) 62 + (char) 190;
        String str8 = "" + (char) 61 + (char) 221;
        String str9 = ""+(char)60 + (char)252;
        String str10 =""+(char)2112;
        String str11 = ""+(char)1+(char)1151;
        String str12 = ""+(char)59+(char)283;
        String str13 = ""+(char)58+(char)314;
        String str15 = "123";
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());
        System.out.println(str3.hashCode());
        System.out.println(str4.hashCode());
        System.out.println(str5.hashCode());
        System.out.println(str6.hashCode());
        System.out.println(str7.hashCode());
        System.out.println(str8.hashCode());
        System.out.println(str9.hashCode());
        System.out.println(str10.hashCode());
        System.out.println(str11.hashCode());
        System.out.println(str12.hashCode());
        System.out.println(str13.hashCode());
        System.out.println(16 & str9.hashCode());
        System.out.println(16 & str15.hashCode());
        System.out.println(Math.floor(10/3.0));
        System.out.println(map.put(str1, Integer.MAX_VALUE));
        map.put(str2, Integer.MIN_VALUE);
        map.put(str3, Integer.MAX_VALUE);
        map.put(str4, Integer.MIN_VALUE);
        map.put(str5, Integer.MAX_VALUE);
        map.put(str6, Integer.MIN_VALUE);
        map.put(str7, Integer.MAX_VALUE);
        map.put(str8, Integer.MIN_VALUE);
        map.put(str9, Integer.MIN_VALUE);
        map.put(str10, Integer.MIN_VALUE);
        map.put(str11, Integer.MIN_VALUE);
        map.put(str12, Integer.MIN_VALUE);
        map.put(str13, Integer.MIN_VALUE);

    }

    public void testConrrentMap(){
        ConcurrentMap<String,Object> map =  new ConcurrentHashMap();
        Hashtable<String,Object> table = new Hashtable();
        table.put("1", 1);
        map.put("1", 1);
    }


    @Test
    public void testReplace(){
        List<Name> nameList = new ArrayList<Name>();
        Name name1 = new Name("1");
        Name name2 = new Name("2");
        Name name3 = new Name("1");
        nameList.add(name1);
        nameList.add(name2);
        nameList.add(name3);

        String result = nameList.stream().map(Name::getName).distinct().collect(Collectors.joining(","));
        System.out.println(result);
    }

    @Data
    @AllArgsConstructor
    static class Name{
        String name;
    }


    @Test
    public  void testDfs() {

        String[] array = {"1","3","2"};
        List<String> list = Arrays.asList(array);
        Set<String> hs = new HashSet<>();
        dfs(list, "", hs);
        for(String str : hs){
            System.out.println(str);
        }
    }


    private  void dfs(List<String> candidate,String prefix, Set<String> result){
        if(!StringUtils.isEmpty(prefix) && !result.contains(prefix) && candidate.isEmpty()){
            result.add(prefix);
        }
        for(int i=0; i<candidate.size(); i++){
            List<String> temp = new LinkedList<>(candidate);

            String item = temp.remove(i);
            dfs(temp,appendKey(prefix,item) , result);
        }
    }

    private String appendKey(String prefix,String nextfix){
        if(StringUtils.isEmpty(prefix)){
            return String.valueOf(nextfix);
        }
        return String.format("%s,%s",prefix,nextfix);
    }

    @Test
    public void testIteratorRemove(){
        List<String> names = new ArrayList<>();
        names.add("a");
        names.add("b");
        names.add("c");
        names.add("d");
        Iterator<String> iterator1 = names.iterator();
        Iterator<String> iterator2 = names.iterator();
        iterator1.next();
        iterator1.remove();
        iterator2.next(); // 运行结果？
    }

    public static class AcTrie{
        Map<Character,AcTrie> nodes;
        AcTrie fail;
        boolean isEnd;
        int length;

        public AcTrie(){
            nodes = new HashMap<>();
            isEnd = false;
            length=1;
        }

    }

    public void insert(AcTrie root,String temp){
        char[] tempCharArr = temp.toCharArray();
        AcTrie cursor = root;
        for(Character character : tempCharArr){
            if(cursor.nodes.get(character)==null){
                AcTrie acTrie = new AcTrie();
                acTrie.length = root.length+1;
                cursor.nodes.put(character,acTrie);
            }
            cursor=cursor.nodes.get(character);
        }
        cursor.isEnd=true;
    }


    public void buildFailPointer(AcTrie root){
        Queue<AcTrie> queue = new ArrayDeque<>();
        queue.add(root);
        root.fail=null;
        while(!queue.isEmpty()){
            AcTrie cursor = queue.remove();

            for(Character character : cursor.nodes.keySet()){
                if(cursor==root){
                    cursor.nodes.get(character).fail = root;
                    continue;
                }
                AcTrie cursorFail = cursor.fail;
                while(cursorFail!=null){
                    if(cursorFail.nodes.get(character)!=null){
                        cursor.nodes.get(character).fail =
                                cursorFail.nodes.get(character);
                        break;
                    }
                    cursorFail =cursorFail.fail;
                }
                if(cursorFail==null){
                    cursor.nodes.get(character).fail = root;
                }
                queue.add(cursor.nodes.get(character));
            }
        }
    }

    public void match(AcTrie root,String text){
        char[] textArr = text.toCharArray();
        AcTrie cursor = root;
        for(int i=0;i<text.length();i++){
            Character character = textArr[i];
            while(cursor!=root && cursor.nodes.get(character) == null){
                cursor = cursor.fail;

            }
            if(cursor.nodes.get(character)!=null){
                cursor = cursor.nodes.get(character);
            }

            if(cursor.isEnd){
                Integer index = i-cursor.length;
                System.out.println(String.format("敏感词:%s,字符串位置:%d"
                        ,text.substring(index,i),index));
            }
        }
    }
}
