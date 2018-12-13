/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.SecretKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.directory.api.util.Base64;
import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestSecure {
    
    @Test
    public void testSecure() throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] datas = "abc".getBytes();
        byte[] result = md.digest(datas);
        System.out.println(new String(Base64.encode(result)));
        
        md = MessageDigest.getInstance("SHA-1");
        result = md.digest(datas);
        System.out.println(converString(result));
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
    
    @Test
    public void testDsa() throws NoSuchAlgorithmException, FileNotFoundException, IOException, InvalidKeyException, SignatureException, ClassNotFoundException, InvalidKeySpecException{
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
    public void testDes() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
         Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
         String Algorithm="DES"; // 定义 加密算法 , 可用 DES,DESede,Blowfish 
         String myinfo="本周参与开发，发现portal的服务调用那块代码复用率很高，重复的工作很多。考虑到如果提高代码抽象度，可能会给项目本身以及他人学习带来复杂度，我觉得这块可以编写一个通用的模板。"; 
         KeyGenerator keygen = KeyGenerator.getInstance(Algorithm); 
         SecretKey deskey = keygen.generateKey();
         byte[] deskeyEncodes = deskey.getEncoded();
         
         System.out.println("加密前的二进串 :"+new String(Base64.encode(myinfo.getBytes()))); 
         System.out.println("加密前的信息 :"+myinfo); 
         
         Cipher c1 = Cipher.getInstance(Algorithm); 
         c1.init(Cipher.ENCRYPT_MODE,deskey); 
         byte[] cipherByte=c1.doFinal(myinfo.getBytes()); 
         System.out.println("加密后的二进串 :"+new String(Base64.encode(cipherByte))); 
         
        // 解密
         Cipher c2 = Cipher.getInstance(Algorithm); 
         DESKeySpec desSpect = new DESKeySpec(deskeyEncodes);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
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
}
