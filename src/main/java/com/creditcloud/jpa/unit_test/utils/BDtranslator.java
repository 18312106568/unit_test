package com.creditcloud.jpa.unit_test.utils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class BDtranslator {

    static MessageDigest md5 ;
    static Gson gson;
    static{
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        gson = new Gson();
    }

    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f' };

    public static String APPID = "20190618000308386";

    private static String SECERT = "7p0eIgR44NhWuyvtf67p";

    private static String HTTP_URL = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private static String HTTPS_URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    private static String PARAM_FORMAT = "q=%s&from=%s&to=%s&appid=%s&salt=%s&sign=%s";

    public static String translate(String query){
        Param param = new Param();
        param.query = query;
        param.appid = APPID;
        param.from = "zh";
        param.to = "en";
        param.salt = String.valueOf(System.nanoTime());
        param.sign = getSign(param);
        try {
           Result result = gson.fromJson(translate(param),Result.class) ;
           List<TransResult> transResults = result.getTransResult();
           if(transResults==null || transResults.isEmpty()){
               return "";
           }
           return transResults.get(0).dst;
        } catch (IOException e) {
            return "";
        }
    }

    public static String translate(Param param) throws IOException {
        String paramStr = format(param);
        String result = HttpUtil.doPost(HTTP_URL,"",paramStr,Boolean.FALSE);
        return result;
    }

    public static String format(Param param){
        return String.format(PARAM_FORMAT,param.query,param.from,param.to,param.appid,param.salt,param.sign);
    }



    /**
     * appid+q+salt+密钥
     * @param param
     * @return
     */
    public static String getSign(Param param){
        StringBuilder sb = new StringBuilder(APPID);
        sb.append(param.query).append(param.salt).append(SECERT);
        return byteArrayToHex(md5.digest(sb.toString().getBytes()));
    }

    private static String byteArrayToHex(byte[] byteArray) {
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);

    }

    private static String converString(byte[] datas){
        StringBuilder sb = new StringBuilder();
        for(byte data:datas){
            int i = data & 0xff;
            if(i<16){
                sb.append("0").append(Integer.toHexString(i));
            }else{
                sb.append(Integer.toHexString(i));
            }
        }
        return sb.toString();
    }

    /**
     * q	TEXT	Y	请求翻译query	UTF-8编码
     * from	TEXT	Y	翻译源语言	语言列表(可设置为auto)
     * to	TEXT	Y	译文语言	语言列表(不可设置为auto)
     * appid	INT	Y	APP ID	可在管理控制台查看
     * salt	INT	Y	随机数
     * sign	TEXT	Y	签名	appid+q+salt+密钥 的MD5值
     */
    @Data
    public static class Param{

        @SerializedName("q")
        private String query;

        @SerializedName("from")
        private String from;

        @SerializedName("to")
        private String to;

        @SerializedName("appid")
        private String appid;

        @SerializedName("salt")
        private String salt;

        @SerializedName("sign")
        private String sign;
    }

    @Data
    public static class Result{

        @SerializedName("error_code")
        private Integer errorCode;

        @SerializedName("error_msg")
        private String errorMsg;

        private String from;

        private String to;

        @SerializedName("trans_result")
        private List<TransResult> transResult;

    }

    @Data
    public static class TransResult{
        private String src;

        private String dst;
    }
}
