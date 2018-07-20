package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.hello.QuoteClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UnitTestApplication {

	public static void main(String[] args) {
            
            SpringApplication.run(UnitTestApplication.class, args);
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
