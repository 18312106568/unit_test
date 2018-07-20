/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.configuration;

/**
 *
 * @author MRB
 */

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

//@Configuration
public class QuoteConfiguration {

	//@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.creditcloud.jpa.unit_test.hello.wsdl");
		return marshaller;
	}

	//@Bean
        /*
	public QuoteClient quoteClient(Jaxb2Marshaller marshaller) {
		QuoteClient client = new QuoteClient();
		client.setDefaultUri("http://opentest.guanmaoyun.net/service/ServiceProviderFacade");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}*/

}
