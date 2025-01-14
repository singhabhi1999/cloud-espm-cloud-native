package com.sap.refapps.espm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This is the application context initializer class
 * used to activate the profile based on environment.
 *
 */
public class SaleApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger logger = LoggerFactory.getLogger(SaleApplicationContextInitializer.class);

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextInitializer#initialize(org.springframework.context.ConfigurableApplicationContext)
	 */
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		var applicationEnvironment = applicationContext.getEnvironment();
		var cloud = getCloud();
		if (cloud != null) {
			logger.info("**********Initializing the application context for cloud env**********");
			applicationEnvironment.setActiveProfiles("cloud");

		} else {
			logger.info("**********Initializing the application context for local env**********");
			applicationEnvironment.setActiveProfiles("local");
		}

	}

	/** 
	 * Returns cloud object if the application runs on cloud environment
	 * 
	 * @return cloud object if the application runs on cloud environment
	 */
	private Cloud getCloud() {
		try {
			var cloudFactory = new CloudFactory();
			return cloudFactory.getCloud();
		} catch (CloudException ce) {
			logger.error("no suitable cloud found");
			return null;
		}
	}

}
