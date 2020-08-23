package com.creditcloud.jpa.unit_test;

import org.junit.Test;

import java.util.*;

public class TestListNode {
    public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }

    /**
     * 两数相加
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;

        int num1 = l1.val;
        int num2 = l2.val;

        ListNode p1 = l1.next;
        ListNode p2 = l2.next;

        int sum = carry+num1+num2;

        ListNode result = new ListNode(sum%10);
        ListNode carryNode = result;
        carry = sum/10;

        while(p1!=null || p2!=null || carry !=0){
            num1 = p1==null?0:p1.val;
            num2 = p2==null?0:p2.val;

            sum = carry+num1+num2;
            carry = sum/10;
            p1 = p1==null?null:p1.next;
            p2 = p2==null?null:p2.next;
            carryNode.next = new ListNode(sum%10);
            carryNode = carryNode.next;
        }

        return result;
    }

    /**
     * 无重复字符的最长子串
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     */
    public int lengthOfLongestSubstring(String s) {
        if(s==null || s.length()==0){
            return 0;
        }

        char[] charArr = s.toCharArray();
        int[] notRepeatArr = new int[s.length()];

        int maxLen = 0;
        for(int i=0;i<s.length()-maxLen;i++){
            boolean[] visited = new boolean[256];
            for(int j=i;j<s.length();j++){
                if(visited[charArr[j]]){
                    break;
                }
                notRepeatArr[i]++;
                visited[charArr[j]]=true;
            }
        }

        for(int notRepeatLong : notRepeatArr){
            maxLen = Math.max(notRepeatLong,maxLen);
        }

        return maxLen;
    }

    @Test
    public void testLengthOfLongestSubstring(){
        int maxLen = lengthOfLongestSubstring("abcabcbb");
        System.out.println(maxLen);
    }


    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        this.candidatesLen = candidates.length;
        this.candidates = candidates;
        this.target = target;
        mem = new boolean[this.candidatesLen][this.target+1];
        this.result = new ArrayList<>();
        this.resultStrArr = new HashSet<>();
      //  List<Integer> numList = new ArrayList<>();
        quickSort(this.candidates,0,candidatesLen-1);
        selectNum(0,0,"");
        for(String resultStr : resultStrArr){
            String[] numStrArr = resultStr.split(",");
            List<Integer> numList = new ArrayList();
            for(String numStr : numStrArr) {
                if(numStr.isEmpty()){
                    continue;
                }
                numList.add(Integer.parseInt(numStr));
            }
            result.add(numList);
        }
        return result;
    }



    private int[] candidates ;  // 候选数组
    private int candidatesLen ; // 候选数个数
    private int target ; // 目标值
    private boolean[][] mem ; // 备忘录，默认值false
    private Set<String> resultStrArr;
    private List<List<Integer>> result ; //符合结果

    private boolean states[][];

    /**
     * 快速排序
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right)
            return;
        int base = arr[right];
        int i = left;
        int j = right;
        while (i!=j){

            while (j>i&&arr[i]<=base) {
                i++;
            }
            while (j>i&&arr[j]>=base) {
                j--;
            }

            int tmp = arr[j];
            arr[j]=arr[i];
            arr[i]=tmp;
        }
        arr[right]=arr[i];
        arr[i]=base;
        quickSort(arr,left,i-1);
        quickSort(arr,i+1,right);
    }

    public void selectNum(int i, int iTarget,List<Integer> numList) {

        if (iTarget == target ) { // iTarget==target表示符合条件
            result.add(numList);
            return;
        }
        if(i==candidatesLen){ //i==candidatesLen 表示所有侯选数都考察完了
            return;
        }
//        if (mem[i][iTarget]) return; // 重复状态
//        mem[i][iTarget] = true; // 记录(i, iTarget)这个状态
        selectNum(i+1, iTarget,numList); // 选择不选第i个数字

        if (iTarget + candidates[i] <= target) {
            List newNumList = new ArrayList();
            for(Integer num : numList){
                newNumList.add(num);
            }
            newNumList.add(candidates[i]);
            selectNum(i+1,iTarget+candidates[i],newNumList); // 选择第i个数字
        }
    }

    public void selectNum(int i, int iTarget,String numStr) {

        if (iTarget == target ) { // iTarget==target表示符合条件
            resultStrArr.add(numStr);
            return;
        }
        if(i==candidatesLen){ //i==candidatesLen 表示所有侯选数都考察完了
            return;
        }
//        if (mem[i][iTarget]) return; // 重复状态
//        mem[i][iTarget] = true; // 记录(i, iTarget)这个状态
        selectNum(i+1, iTarget,numStr); // 选择不选第i个数字

        if (iTarget + candidates[i] <= target) {
            numStr = numStr + ","+candidates[i];
            selectNum(i+1,iTarget+candidates[i],numStr); // 选择第i个数字
        }
    }

    @Test
    public void testCombinationSum2(){
        int[] candidates =  {10,1,2,7,6,1,5};
        int target = 8;
        List<List<Integer>> result = combinationSum2(candidates,target);
        System.out.println(result);
    }

    @Test
    public void testCombinationSum3(){
        int[] candidates =  {10,1,2,7,6,1,5};
        int target = 8;
        List<List<Integer>> result = combinationSum3(candidates,target);
        System.out.println(result);
    }


    @Test
    public void testCombinationSum(){
        int[] candidates =  {8,1,2,7,6,1,5};
        int target = 8;
        List<List<Integer>> result = combinationSum(candidates,target);
        System.out.println(result);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        this.candidatesLen = candidates.length;
        this.candidates = candidates;
        this.target = target;
        this.states = new boolean[candidatesLen][target+1];
        quickSort(this.candidates,0,candidatesLen-1);
        dynamicProgram();
        List<List<Integer>> result = new ArrayList<>();
        if(states[0][target]){
            result.addAll(selectList(0,target));
        }
        for(int i=candidatesLen-1;i>0;i--){
            if(states[i][target] && target-candidates[i]>=0
                    && states[i-1][target-candidates[i]]){
                List<List<Integer>> selectLists = selectList(i,target);
                for(List<Integer> selectList : selectLists){
                    if(result.contains(selectList)){
                        continue;
                    }
                    result.add(selectList);
                }
            }
        }
        return result;
    }

    public void dynamicProgram(){
        states[0][0] = true;
        if(candidates[0]<=target){
            states[0][candidates[0]]=true;
        }
        for(int i=1;i<candidatesLen;i++){
            for(int j=0;j<=target;j++){ //不选择第i个candidates
                if(states[i-1][j]) states[i][j]=true;
            }
            for(int j=0;j<=target-candidates[i];j++){ //选择第i个candidates
                if(states[i-1][j]) states[i][j+candidates[i]]=true;
            }
        }
        return;
    }

    /**
     * 通过动态规划出来的结果反推选择项
     * @param candidatesIndex
     * @param nowTaget
     * @return
     */
    private List<List<Integer>> selectList(int candidatesIndex,int nowTaget){
        List<List<Integer>>  result = new ArrayList<>();

        if(candidatesIndex==0){
            List<Integer> startSelect = new ArrayList<>();
            result.add(startSelect);
            if(nowTaget!=0) {
                startSelect.add(nowTaget);
            }
            return result;
        }

        if(states[candidatesIndex][nowTaget]){
            if(nowTaget-candidates[candidatesIndex]>=0
                    && states[candidatesIndex-1][nowTaget-candidates[candidatesIndex]]){
                result.addAll(selectList(candidatesIndex-1
                        ,nowTaget-candidates[candidatesIndex]));
                for(List<Integer> selectList : result){
                    selectList.add(candidates[candidatesIndex]);
                }
            }
            if(nowTaget!=this.target && states[candidatesIndex-1][nowTaget]){

                result.addAll(selectList(candidatesIndex-1,nowTaget));

            }

        }

        return result;
    }

    private void abc(int[] candidates,  int start, int target, List<List<Integer>> rs, List<Integer> temp) {
        if(start > candidates.length ) {
            return;
        }

        if(target<0){
            return ;
        }
        if(target == 0) {
            rs.add(new ArrayList<>(temp));
        }
        for(int i = start ; i < candidates.length && candidates[i] <= target; i++ ) {
            if(i > start && candidates[i] == candidates[i-1]) {
                continue;
            }
            temp.add(candidates[i]);
            abc(candidates, i+1, target -candidates[i], rs, temp);
            temp.remove(temp.size() - 1);
        }
    }

    public List<List<Integer>> combinationSum3(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> rs = new ArrayList<>();
        abc(candidates, 0, target, rs, new ArrayList<>());
        return rs;


    }
}
