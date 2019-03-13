package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.hello.QuoteClient;
import com.creditcloud.jpa.unit_test.utils.SpringUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.instrument.Instrumentation;

@SpringBootApplication
public class UnitTestApplication {

    public static Instrumentation instrumentation;

    public static void premain(String agentArg, Instrumentation inst){
        instrumentation = inst;
    }

	public static void main(String[] args) {
            System.out.println("============================>");
            SpringUtil.setApplicationContext(
                    SpringApplication.run(UnitTestApplication.class, args));
	}
        /*
        @Bean
	CommandLineRunner lookup(QuoteClient quoteClient) {
		return args -> {
			String ticker = "FINANCE_USER_UPLOAD_DATA";

			if (args.length > 0) {
				ticker = args[0];
			}
			//GetQuoteResponse response = quoteClient.getQuote(ticker);
			//System.err.println(response.getGetQuoteResult());
		};
	}*/
}
