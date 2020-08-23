package com.creditcloud.jpa.unit_test.search;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StringSearch {

    private static final int SIZE = 256; // 全局变量或成员变量
    private void generateBC(char[] modeChars, int modeCharsLen, int[] bc) {
        // 初始化bc
        for (int i = 0; i < SIZE; ++i) {
            bc[i] = -1;
        }
        for (int i = 0; i < modeCharsLen; ++i) {
            // 计算b[i]的ASCII值
            int ascii = (int)modeChars[i];
            bc[ascii] = i;
        }
    }

    /**
     * 主要根据modeChars 加载前后缀辅助数组
     * @param modeChars
     * @param modeCharsLen
     * @param suffix
     * @param prefix
     */
    // modeChars表示模式串，modeCharsLen表示长度，suffix，prefix数组事先申请好了
    private void generateGS(char[] modeChars, int modeCharsLen, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < modeCharsLen; ++i) { // 初始化
            suffix[i] = -1;
            prefix[i] = false;
        }
        //遍历模式串
        for (int i = 0; i < modeCharsLen - 1; ++i) { // modeChars[0, i]
            int j = i;
            int k = 0; // 公共后缀子串长度
            // 与modeChars[0, modeCharsLen-1]求公共后缀子串
            while (j >= 0 && modeChars[j] == modeChars[modeCharsLen-1-k]) {
                --j;
                ++k;
                suffix[k] = j+1; //j+1表示公共后缀子串在modeChars[0, i]中的起始下标
            }
            if (j == -1) prefix[k] = true; //如果公共后缀子串也是模式串的前缀子串
        }
    }

    /**
     * 核心目标，如何控制主串位移光标 i
     * @param mainChars
     * @param mainCharsLen
     * @param modeChars
     * @param modeCharsLen
     * @return
     */
    public int bm(char[] mainChars, int mainCharsLen, char[] modeChars, int modeCharsLen) {
        // 记录模式串中每个字符最后出现的位置
        int[] bc = new int[SIZE];
        // 构建坏字符哈希表
        generateBC(modeChars, modeCharsLen, bc);
        int[] suffix = new int[modeCharsLen];
        boolean[] prefix = new boolean[modeCharsLen];
        generateGS(modeChars, modeCharsLen, suffix, prefix);

        // i表示主串与模式串对齐的第一个字符
        int i = 0;
        while (i <= mainCharsLen - modeCharsLen) {
            int j;
            for (j = modeCharsLen - 1; j >= 0; --j) { // 模式串从后往前匹配
                if (mainChars[i+j] != modeChars[j]) break; // 坏字符对应模式串中的下标是j
            }
            if (j < 0) {
                return i; // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            }
            //坏字符需要跳跃的字符个数
            int x =  (j - bc[(int)mainChars[i+j]]);
            int y = 0; if (j < modeCharsLen-1) { // 如果有好后缀的话
                 y = moveByGS(j, modeCharsLen, suffix, prefix);
            }
            // 这里等同于将模式串往后滑动j-bc[(int)a[i+j]]位
            i = i + Math.max(x, y);
        }
        return -1;
    }

    // j表示坏字符对应的模式串中的字符下标; m表示模式串长度
    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j; // 好后缀长度
        if (suffix[k] != -1) return j - suffix[k] +1;
        for (int r = j+2; r <= m-1; ++r) {
            if (prefix[m-r] == true) {
                return r;
            }
        }
        return m;
    }

    @Test
    public void testGenerateGs(){
        String modeChars = "abcabcbcacbdcacb";
        char[] modeCharArr = modeChars.toCharArray();
        int[] suffix = new int[modeChars.length()];
        boolean[] prefix = new boolean[modeChars.length()];
        for(int i=0;i<modeCharArr.length;i++){
            System.out.print(modeCharArr[i]);
            System.out.print(' ');
        }
        System.out.println();
        generateGS(modeChars.toCharArray(),modeChars.length(),suffix,prefix);
        for(int i=0;i<suffix.length;i++ ){
           log.info("{}-{}-{}",i,suffix[i],prefix[i]);
        }
    }

}
