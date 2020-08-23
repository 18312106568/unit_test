package com.creditcloud.jpa.unit_test.designModel;

import co.paralleluniverse.common.util.VisibleForTesting;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class IDGenerator {
    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);
    public static String generate() {
        String id = "";
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] tokens = hostName.split("\\.");
            if (tokens.length > 0) {
                hostName = tokens[tokens.length - 1];
            }
            char[] randomChars = new char[8];
            int count = 0;
            Random random = new Random();
            while (count < 8) {
                int randomAscii = random.nextInt(122);
                if (randomAscii >= 48 && randomAscii <= 57) {
                    randomChars[count] = (char)('0' + (randomAscii - 48));
                    count++;
                } else if (randomAscii >= 65 && randomAscii <= 90) {
                    randomChars[count] = (char)('A' + (randomAscii - 65));
                    count++;
                } else if (randomAscii >= 97 && randomAscii <= 122) {
                    randomChars[count] = (char)('a' + (randomAscii - 97));
                    count++;
                }
            }
            id = String.format("%s-%d-%s", hostName,
                    System.currentTimeMillis(), new String(randomChars));
        } catch (UnknownHostException e) {
            logger.warn("Failed to get the host name.", e);
        }
        return id;
    }



    public interface IdGenerator {
        String generate();
    }
    public interface LogTraceIdGenerator extends IdGenerator {
    }
    public static class RandomIdGenerator implements IdGenerator {
        private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator.class);
        @Override
        public String generate() {
            String substrOfHostName = getLastfieldOfHostName();
            long currentTimeMillis = System.currentTimeMillis();
            String randomString = generateRandomAlphameric(8);
            String id = String.format("%s-%d-%s",
                    substrOfHostName, currentTimeMillis, randomString);
            return id;
        }
        private String getLastfieldOfHostName() {
            String substrOfHostName = null;
            try {
                String hostName = InetAddress.getLocalHost().getHostName();
                substrOfHostName = getLastSubstrSplittedByDot(hostName);
            } catch (UnknownHostException e) {
                logger.warn("Failed to get the host name.", e);
            }
            return substrOfHostName;
        }
        @VisibleForTesting
        protected String getLastSubstrSplittedByDot(String hostName) {
            String[] tokens = hostName.split("\\.");
            String substrOfHostName = tokens[tokens.length - 1];
            return substrOfHostName;
        }
        @VisibleForTesting
        protected String generateRandomAlphameric(int length) {
            char[] randomChars = new char[length];
            int count = 0;
            Random random = new Random();
            while (count < length) {
                int maxAscii = 'z';
                int randomAscii = random.nextInt(maxAscii);
                boolean isDigit= randomAscii >= '0' && randomAscii <= '9';
                boolean isUppercase= randomAscii >= 'A' && randomAscii <= 'Z';
                boolean isLowercase= randomAscii >= 'a' && randomAscii <= 'z';
                if (isDigit|| isUppercase || isLowercase) {
                    randomChars[count] = (char) (randomAscii);
                    ++count;
                }
            }
            return new String(randomChars);
        }
    }


    @Test
    public void testGetLastSubstrSplittedByDot() {
        RandomIdGenerator idGenerator = new RandomIdGenerator();
        String actualSubstr = idGenerator.getLastSubstrSplittedByDot("field1.field2.field3");
        Assert.assertEquals("field3", actualSubstr);
        actualSubstr = idGenerator.getLastSubstrSplittedByDot("field1");
        Assert.assertEquals("field1", actualSubstr);
        actualSubstr = idGenerator.getLastSubstrSplittedByDot("field1#field2$field3");
        Assert.assertEquals("field1#field2#field3", actualSubstr);
    }
    // 此单元测试会失败，因为我们在代码中没有处理hostName为null或空字符串的情况
    // 这部分优化留在第36、37节课中讲解
    @Test
    public void testGetLastSubstrSplittedByDot_nullOrEmpty() {
        RandomIdGenerator idGenerator = new RandomIdGenerator();
        String actualSubstr = idGenerator.getLastSubstrSplittedByDot(null);
        Assert.assertNull(actualSubstr);
        actualSubstr = idGenerator.getLastSubstrSplittedByDot("");
        Assert.assertEquals("", actualSubstr);
    }
    @Test
    public void testGenerateRandomAlphameric() {
        RandomIdGenerator idGenerator = new RandomIdGenerator();
        String actualRandomString = idGenerator.generateRandomAlphameric(6);
        Assert.assertNotNull(actualRandomString);
        Assert.assertEquals(6, actualRandomString.length());
        for (char c : actualRandomString.toCharArray()) {
            Assert.assertTrue(('0' < c && c > '9') || ('a' < c && c > 'z') || ('A' < c && c < 'Z'));
        }
    }
    // 此单元测试会失败，因为我们在代码中没有处理length<=0的情况
    // 这部分优化留在第36、37节课中讲解
    @Test
    public void testGenerateRandomAlphameric_lengthEqualsOrLessThanZero() {
        RandomIdGenerator idGenerator = new RandomIdGenerator();
        String actualRandomString = idGenerator.generateRandomAlphameric(0);
        Assert.assertEquals("", actualRandomString);
        actualRandomString = idGenerator.generateRandomAlphameric(-1);
        Assert.assertNull(actualRandomString);
    }

}