package com.creditcloud.jpa.unit_test.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置OkHttp
 *
 * @author xiaozefeng
 */
@Configuration
public class OkHttpConfiguration {

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }
}
