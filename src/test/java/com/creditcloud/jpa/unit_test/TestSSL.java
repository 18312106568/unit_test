/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import org.junit.Test;
/**
 *
 * @author MRB
 */
public class TestSSL {
    @Test
    public void testSslContext() throws SSLException{
        final SslContext sslCtx;
        sslCtx = SslContextBuilder.forClient()
                  .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        for(String cipher :sslCtx.cipherSuites()){
            System.out.println(cipher);
        }
        System.out.println(sslCtx);
        System.out.println(System.getProperty("javax.net.ssl.trustStore"));
    }
}
