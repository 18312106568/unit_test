/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;


import com.creditcloud.jpa.unit_test.vo.tp.CrackVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author MRB
 */
public class TestQRCode {
    @Test
    public void testQRCode(){
        String text = "https://www.baidu.com/"; // 二维码内容
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度
        String format = "gif";// 二维码的图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");	// 内容所使用字符集编码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
                // 生成二维码
            File outputFile = new File("d:" + File.separator + "new.gif");
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
    }
    
    @Test
    public void testStringFormat(){
        UUID uuid = UUID.randomUUID(); 
        
        System.out.println(
           new String(Base64 .getEncoder().encode((uuid.toString()+new Date().getTime())
                .substring(1).getBytes())).replaceAll("\\/", "-").replaceAll("\\+", "*").replaceAll("\\=", "_"));
        System.out.println(String.format("试算成功条数：%d", 10));
    }
    
    @Test
    public void testEquals(){
        Integer integer = new Integer(2018);
        Integer integer2 = new Integer(2018);
        int i = 2018;
        System.out.println(i==integer);
        System.out.println(integer==i);
        System.out.println(integer2==i);
        System.out.println(integer2==integer);
    }
    
    @Test
    public void testSb(){
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ( ")
            .append("select  ")
            .append("hl.CAP_REQUEST_NO AS capRequestNo, ")
            .append("hl.`STATUS` AS status, ")
            .append("tb.`NAME` AS corporationName, ")
            .append("hl.TIMERECORDED AS timeRecorded, ")
            .append("hl.AMOUNT AS amount, ")
            .append("hl.RATE AS rate, ")
            .append("hl.DAYS as term, ")
            .append("hl.LOAN_ID AS loanId, ")
            .append("tl.TAXCANCEL AS taxCancel ")
            .append(" from TB_HXB_LOAN hl ")
            .append("LEFT JOIN TB_USER tu ON tu.ID = hl.USER_ID ")
            .append("LEFT JOIN TB_LOAN tl ON hl.LOAN_ID = tl.ID ")
            .append(" LEFT JOIN TB_CORPORATION_BASE tb ON tb.ORG_CODE = tu.ORG_CODE)t ");
        System.out.println(sql.toString());
    }
    
    @Test
    public void testDateUtil() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    //    int distance = DateUtils.getDistance(sdf.parse("20180808 13:12:12"), sdf.parse("20180809 12:12:12"));
      //  System.out.println(distance);
    }
   
    @Test
    public void testChangeJson() throws NoSuchFieldException {
        Class clazz = CrackVo.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.print(field.getName() + "===>");
            JsonProperty ann = field.getAnnotation(JsonProperty.class);
            if (ann != null) {
                System.out.println(ann.value());
            } else {
                System.out.println("非序列化属性");
            }
        }
    }
    
    @Test
    public void testJsonToMap() throws IOException{
        String json ="{appeal_time=null, appeal_state=1.0, zone=全区, extend={rank=1.0}, reduce_state=1.0, reason=游戏作弊, start_stmp=1.523024851E9, game_name=地下城与勇士, free_state=1.0, duration=8.64E7, game_id=5.0, type=封号, reduced=0.0, reduce_percent=0.0}";
       
        JsonReader jr = new Gson().newJsonReader(new StringReader(json));
        while(jr.hasNext()){
            System.out.println(jr.nextString());
        }
    }
    
    
    @Test
    public void testMD5(){
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(UUID.randomUUID().toString());
    }
}
