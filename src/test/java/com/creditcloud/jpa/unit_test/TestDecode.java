/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.common.security.SecurityUtils;
import com.creditcloud.common.utils.HxbDaysUtil;
import com.creditcloud.jpa.unit_test.utils.ConverUtil;
import com.creditcloud.jpa.unit_test.vo.DeclarationForm;
import com.creditcloud.jpa.unit_test.vo.ResponseForm;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.sun.javafx.css.Declaration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;  
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.spec.SecretKeySpec;  
   
import org.apache.commons.lang3.StringUtils;  
import org.apache.tomcat.util.codec.binary.Base64;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author MRB
 */
public class TestDecode {
    
     /** 
     * 密钥 
     */  
    private static final String KEY = "efushui201709011";  
      
    /** 
     * 算法 
     */  
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";  
    
    
    
//    @Test
//    public void testCrypto(){
//        try {
//            String pwd = "WPrJ/L6szlO7Z4r/EQs2/Q=="; 
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
//            byte[] raw = pwd.getBytes("utf-8"); 
//            SecretKey secretKey = new SecretKeySpec(raw, "AES"); 
//            System.out.println("密钥的长度为：" +(secretKey.getEncoded().length)); 
//
//            byte[] encrypt = Base64.getDecoder().decode("efushui201709011");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(raw));//使用解密模式初始化 密钥 
//            byte[] decrypt = cipher.doFinal(encrypt); 
//            System.out.println("解密后：" + new String(decrypt));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
  
    @Test
    public  void testCrypto()  {  
        try {
             String mobile = SecurityUtils.getSalt("18312106568");
            System.out.println(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
     
    }  
      
    /** 
     * aes解密 
     * @param encrypt   内容 
     * @return 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encrypt) throws Exception {  
        return aesDecrypt(encrypt, KEY);  
    }  
      
    /** 
     * aes加密 
     * @param content 
     * @return 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content) throws Exception {  
        return aesEncrypt(content, KEY);  
    }  
  
    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }  
  
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return new String(bytes);
    }  
  
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] base64Decode(String base64Code) throws Exception{  
        return StringUtils.isEmpty(base64Code) ? null : Base64.decodeBase64(base64Code.getBytes());  
    }  
  
      
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));  
  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
  
  
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
  
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
     public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128);  
  
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
            byte[] decryptBytes = cipher.doFinal(encryptBytes);  
  
            return new String(decryptBytes);  
        }  
  
  
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    } 
    
    @Test
    public void testBase64(){
        //ptui_checkVC('0','!SZH','\x00\x00\x00\x00\x7d\x86\x50\xf9','ac12e55243e33b212ebea78975358bbe894dac93315a5ad36e015377441f7ad2973f2288f72e8a51256de7ceda319a8c0f236c3506123314','2')
        System.out.println(new String(Base64.encodeBase64(intToByteArray(0x000000007d8650f9))));
        System.out.println(new String(Base64.decodeBase64("fYZQ+Q==".getBytes())));
        System.out.println(new String(Base64.encodeBase64("\\x00\\x00\\x00\\x00\\x7d\\x86\\x50\\xf9".getBytes())));
         System.out.println(new String(Base64.encodeBase64("123abc123abc".getBytes())));
        System.out.println(new String(Base64.decodeBase64("AAAAAH2GUPk=".getBytes())));
        //AAAAAH0/UKi0  
         System.out.println(new String(Base64.decodeBase64("AAAAAH0/UKi0=".getBytes())));
    }
    
    public static byte[] intToByteArray(int a) {
        return new byte[]{
            (byte) ((a >> 24) & 0xFF),
            (byte) ((a >> 16) & 0xFF),
            (byte) ((a >> 8) & 0xFF),
            (byte) (a & 0xFF)
        };
    }
    
    @Test
    public void testRegex(){
        System.out.println("1[2]3,".replaceAll("[12\\[\\],]", ""));
    }
    
    @Test
    public void testRegex2() throws ParseException{
        //System.out.println("1[2]31,".replace("[12\\[\\],]", ""));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        System.out.println( HxbDaysUtil.calculateDays(sdf.parse("20150514 23:00:00"),sdf.parse("20150515 19:00:01")) );
        
    }
    
    @Test
    public void testRegex3(){
        
        String sql = "CREATE TABLE `TB_SERIAL` (\t\n\n\n\t"
                + "  `ID` varchar(36) NOT NULL,\n"
                + "  `NAME` varchar(125) DEFAULT NULL,\n"
                + "  `SEQ` int(11) NOT NULL,\n"
                + "  `TIMEDATE` datetime NOT NULL,\n"
                + "  `TYPE` varchar(50) NOT NULL,\n"
                + "  PRIMARY KEY (`ID`)\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        System.out.println(sql);
        sql = sql.trim().toLowerCase().replaceAll("`", "").replaceAll("(\\s*|\t*)((\r\n)|\n)(\\s*|\t*)", "");
        
        System.out.println(sql.substring(sql.indexOf("(")+1,sql.lastIndexOf(")")));
//       String str1 = "abc\n012\t3\ncdr";
//      String pattern = "(123)1*\\1";
//      Pattern pt = Pattern.compile(pattern);
//      Matcher matcher = pt.matcher(str1);
//        while (matcher.find()) {
//           System.out.println(matcher.group());
//        }
//      System.out.println(str1.replaceAll("(\t)|[\r\n\t\\s]", "$1"));
//      System.out.println("0.123".replaceAll("^([1-9])(\\d{3}.*$)","$1$2"));
 //       System.out.println("3123".replaceAll("([34]*)(1|2)(\\1)", "$2"));
        String sqlSub = "id varchar(50) default null commen '123'";
        if(sqlSub.contains("comment")){
            System.out.println(sqlSub.substring(sqlSub.indexOf("comment")));
        }
        //System.out.println("comment '123'".contains("comment"));
        String str1 = "varchar(150)";
        String pattern_num = "([^0-9]*)(\\d+)([^0-9]*)";
        Pattern np = Pattern.compile(pattern_num);
        System.out.println(str1.replaceAll(np.pattern(), "$2"));
        System.out.println(String.class.getName());
    }
    
    @Test
    public void testAppend() {
        try {
            File file = new File("D:\\program\\sql.txt");
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            while (is.read(bytes, 0, 1024) != -1) {
                System.out.println(".append(\""+new String(bytes)+"\")");
            }
            Reader reader = new FileReader(file);
            int i;
            System.out.print(".append(\"");
            while ((i=reader.read())!=-1) {
                if(i==13){
                    continue;
                }
                if(i==10){
                    System.out.print("\")\n.append(\"");
                    continue;
                }
                System.out.print((char)i);
            }
            System.out.println("\")");
            System.out.println("--------------");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s;
            while((s=bufferedReader.readLine())!=null){
                System.out.println(".append(\""+s+"\")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFileToModel() throws FileNotFoundException, IOException{
        File file = ResourceUtils.getFile("classpath:YHX-D.txt");
        BufferedReader bufReader = new BufferedReader(new FileReader(file));
        String line ;
        while((line=bufReader.readLine())!=null){
            String[] columns = line.split("[\t ]");
            System.out.println("/**");
            System.out.println("* "+new String(columns[0].getBytes(),"utf-8"));
            System.out.println("*/");
            System.out.println("@JsonProperty(\""+columns[1]+"\")");
            System.out.println("private String "+columns[1]+";");
            System.out.println();
        }
    }
    
    @Test
    public void testFileToSql() throws FileNotFoundException, IOException{
        File file = ResourceUtils.getFile("classpath:YHX-D.txt");
        BufferedReader bufReader = new BufferedReader(new FileReader(file));
        String line ;
        while((line=bufReader.readLine())!=null){
            String[] columns = line.split("[\t ]");
            System.out.println(ConverUtil.changeCameToHung(columns[1]).toUpperCase()
                    +" varchar(50) COMMENT '"+new String(columns[0].getBytes(),"utf-8")+"',");
        }
    }
    
    @Test
    public void testModelToSql(){
        Class clazz = DeclarationForm.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty==null) {
                continue;
            }
            System.out.println(ConverUtil.changeCameToHung(field.getName()).toUpperCase()
                    +" varchar(50) ");
        }
    }
    
    @Test
    public void testGson(){
            String json = "{\n"
            + "    \"COOP_CODE\": \"YHX000\",\n"
            + "    \"CODE\": \"200\",\n"
            + "    \"MSG\": \"\",\n"
            + "    \"CONTENTS\": {\n"
            + "        \"create_time\": \"1422850629\",\n"
            + "        \"SnCount\": 116\n"
            + "    },\n"
            + "    \"SIGNATURE\": \"jlakyIfnCnSN2fR/CGv9suQW0KAGj94gzQkpdRmjt4SlnUog80LV/yTcVNanwWeF+nbJid/EqUqmYv0LQHaWosoaIYA8slfNRKzWEyMz0gNPwFwdnVIXJcwmqPv3Z37WjnnGkZL8Zr8AQ4c+/o6X95eXlRV/pNJ7+RVjbDChAcY=\"\n"
            + "}";
         Gson gson =  new Gson();
         ResponseForm form = gson.fromJson(json, ResponseForm.class);
         System.out.println(form.getCONTENTS());
    
    }
    
    @Test
    public void testBase64Decode(){
         byte[] encrypted1 = new Base64().decode("MD5cG3iBs9BgTw3WLQ+iVw==");
         for(byte b : encrypted1){
              System.out.print(b);
         
         }
        
    }
}
