/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.RC4Util;
import org.apache.directory.api.util.Base64;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author MRB
 */
public class TestSecure {

    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f' };

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

    @Test
    public void testSecure() throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] datas = "2015063000000001apple143566028812345678".getBytes();
        byte[] result = md.digest(datas);
        System.out.println(new String(Base64.encode(result)));
        System.out.println(new String(converString(result)));
        System.out.println(byteArrayToHex(result));
        md = MessageDigest.getInstance("SHA-1");
        result = md.digest(datas);
        System.out.println(converString(result));
        System.out.println(byteArrayToHex(result));
    }

    public String converString(byte[] datas){
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

    public byte[] hexToStr(String hexStr){
        int dataLen = hexStr.length()/2;
        byte[] data = new byte[dataLen];
        for(int i=0;i<dataLen;i++){
            data[i]= (byte)Integer.parseInt(hexStr.substring(2*i,2*i+2),16);
        }
        return data;
    }

    @Test
    public void testHex(){
        String hexStr = converString("123".getBytes());
        System.out.println(hexStr);

        String str = new String(hexToStr(hexStr));
        System.out.println(str);
    }

    @Test
    public void testProdRSAKey() throws NoSuchAlgorithmException, IOException {
        String algorithm = "RSA";
        final String SIGN_ALGORITHMS = "SHA1withRSA";
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(algorithm);

        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("abc".getBytes());
        keygen.initialize(512, secrand);
        keygen.initialize(512);
        //生成密钥对
        KeyPair keys = keygen.generateKeyPair();
        PublicKey pubkey = keys.getPublic();
        PrivateKey prikey = keys.getPrivate();

        System.out.println(pubkey.toString());
        System.out.println(prikey.toString());

        BufferedWriter out = new BufferedWriter(
                new FileWriter(new File("E:\\tmp\\rsapubkey.dat")));
        out.write(Base64.encode(pubkey.getEncoded()));
        out.close();
        out = new BufferedWriter(
                new FileWriter(new File("E:\\tmp\\rsaprikey.dat")));
        out.write(Base64.encode(prikey.getEncoded()));
        out.close();
    }

    @Test
    public void testSHA1withRSA() throws NoSuchAlgorithmException, FileNotFoundException, IOException, InvalidKeyException, SignatureException, ClassNotFoundException, InvalidKeySpecException{
        String algorithm = "RSA";
        final String SIGN_ALGORITHMS = "SHA1withRSA";
//        KeyPairGenerator keygen = KeyPairGenerator.getInstance(algorithm);
//
//        SecureRandom secrand = new SecureRandom();
//        secrand.setSeed("abc".getBytes());
//        keygen.initialize(512, secrand);
//        keygen.initialize(512);
//        //生成密钥对
//        KeyPair keys = keygen.generateKeyPair();
//        PublicKey pubkey = keys.getPublic();
//        PrivateKey prikey = keys.getPrivate();
//
//        System.out.println(pubkey.toString());
//        System.out.println(prikey.toString());
//
//        BufferedWriter out = new BufferedWriter(
//                new FileWriter(new File("E:\\tmp\\rsapubkey.dat")));
//        out.write(Base64.encode(pubkey.getEncoded()));
//        out.close();
//        out = new BufferedWriter(
//                new FileWriter(new File("E:\\tmp\\rsaprikey.dat")));
//        out.write(Base64.encode(prikey.getEncoded()));
//        out.close();

        BufferedReader in = new BufferedReader(
                new FileReader(new File("E:\\tmp\\rsaprikey.dat")));
        String prikeyStr =  in.readLine();
        PKCS8EncodedKeySpec keyspec =
                new PKCS8EncodedKeySpec(Base64.decode(prikeyStr.toCharArray()));
        PrivateKey prikey = KeyFactory.getInstance(algorithm).generatePrivate(keyspec);
        in = new BufferedReader(
                new FileReader(new File("E:\\tmp\\rsapubkey.dat")));
        String pubkeyStr = in.readLine();
        X509EncodedKeySpec pubspec = new X509EncodedKeySpec(Base64.decode(pubkeyStr.toCharArray()));
        PublicKey pubKey = KeyFactory.getInstance(algorithm).generatePublic(pubspec);
        byte[] info = "测试信息123".getBytes();
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(prikey);
        signature.update(info);

        byte[] result = signature.sign();

        char[] encrypt = Base64.encode(result);
        System.out.println(encrypt);
        //System.out.println(Base64.decode(encrypt));

        Signature sigcheck = Signature.getInstance(SIGN_ALGORITHMS);
        sigcheck.initVerify(pubKey);
        sigcheck.update(info);
        System.out.println(sigcheck.verify(Base64.decode(encrypt)));
    }


    @Test
    public void testRSA() throws Exception{
        String RSA_ALGORITHM = "RSA";
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        kpg.initialize(512);
        String data = "123";
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        //得到密钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] buffer = cipher.doFinal(data.getBytes());
        System.out.println(new String(buffer));

        Cipher cipher2 = Cipher.getInstance(RSA_ALGORITHM);
        cipher2.init(Cipher.DECRYPT_MODE, privateKey);
        buffer = cipher2.doFinal(buffer);
        System.out.println(new String(buffer));
    }

    @Test
    public void testDes() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
         Security.addProvider(new com.sun.crypto.provider.SunJCE());
         String Algorithm="DES"; // 定义 加密算法 , 可用 DES,DESede,Blowfish
         String myinfo="-3a6a7d35994df1081eb7f2dda3a2255";
//         KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
//         SecretKey deskey = keygen.generateKey();
//         byte[] deskeyEncodes = deskey.getEncoded();
         String encKey="jyys si eth yaw";
         MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] datas = encKey.getBytes();
         byte[] result = md.digest(datas);
         String desSpecStr = converString(result).substring(0, 16);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
         DESKeySpec desSpect = new DESKeySpec(desSpecStr.getBytes());
         SecretKey deskey = keyFactory.generateSecret(desSpect);
         System.out.println("加密前的二进串 :"+new String(Base64.encode(myinfo.getBytes())));
         System.out.println("加密前的信息 :"+myinfo);

         Cipher c1 = Cipher.getInstance(Algorithm);
         c1.init(Cipher.ENCRYPT_MODE,deskey);
         byte[] cipherByte=c1.doFinal(myinfo.getBytes());
         System.out.println("加密后的二进串 :"+new String(Base64.encode(cipherByte)));

        // 解密
         Cipher c2 = Cipher.getInstance(Algorithm);
         deskey = keyFactory.generateSecret(desSpect);
         c2.init(Cipher.DECRYPT_MODE,deskey);
//        c1 = Cipher.getInstance(Algorithm);
//        c1.init(Cipher.DECRYPT_MODE,deskey);
        byte[] clearByte=c2.doFinal(cipherByte);
        System.out.println("解密后的二进串 :"+new String(Base64.encode(clearByte)));
        System.out.println("解密后的信息 :"+(new String(clearByte)));
    }

    @Test
    public void testAes() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, BadPaddingException, NoSuchPaddingException{
        String Algorithm="AES"; // 定义 加密算法
        String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
        String password = "123456";
        String privilageKey = "2018121323411234";
        String ivKey = "2018121323411234";

        SecretKeySpec secretKeySpec = new SecretKeySpec(privilageKey.getBytes(),Algorithm);
        IvParameterSpec ivSpec = new IvParameterSpec(ivKey.getBytes());
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,ivSpec);

        byte[] encryptedContent = cipher.doFinal(password.getBytes());

        Cipher cipher2 = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec,ivSpec);
        byte[] decryptedContent = cipher2.doFinal(encryptedContent);

        System.out.println(new String(decryptedContent));

    }

    @Test
    public void testRC4() throws NoSuchAlgorithmException {
        String Algorithm = "RC4";   // 定义 加密算法 , RC4
        //加密信息
        String myinfo = "ABC123";
        //明文密钥
        String encKey = "2235";
        System.out.println(RC4Util.encry_RC4_string(myinfo,encKey));
        System.out.println(RC4Util.decry_RC4("7d1944ffce3e",encKey));
//        System.out.println(RC4Util.decry_RC4("10702804683d",encKey));
//        System.out.println(new String(Base64.decode(encKey.toCharArray())));
        SecretKey deskey = new SecretKeySpec(
                encKey.getBytes(), Algorithm);

//        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm);
//        System.out.println(keyGenerator.generateKey().getEncoded().length);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] cipherByte=cipher.doFinal(myinfo.getBytes());
            System.out.println("加密后的16进制串 :"+converString(cipherByte));

            cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE,deskey);
            byte[] decryptByte = cipher.doFinal(cipherByte);
            System.out.println("解密后的字符串："+new String(decryptByte));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3Des(){
        System.out.println(System.getProperty("file.encoding"));
        //加密信息
        String myinfo = "123";
        //明文密钥
        String encKey = "123456788765432112345678";
        String Algorithm = "DESede";   // 定义 加密算法 , 可用 DES,DESede,Blowfish
        SecretKey deskey = new SecretKeySpec(encKey.getBytes(), Algorithm);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] cipherByte=cipher.doFinal(myinfo.getBytes());
            System.out.println("加密后的二进串 :"+new String(Base64.encode(cipherByte)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMysql(){
        System.out.println(converString("123abc123abc".getBytes()));
        System.out.println(converString("403c204e2e7f147d".getBytes()));
        System.out.println("b86f576ce4cfb82b41c9b8c9b89b7b67fd7d5467".length());
        System.out.println("4DCEF8D94E42F4CE7205BB12C6670C12DAB96E46".length());
    }
}
