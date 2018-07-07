/*******************************************************************************
 * Copyright (c) 2018 Jorge Lanza, 
 *                    David Gomez, 
 *                    Luis Sanchez,
 *                    Juan Ramon Santana
 *
 * For the full copyright and license information, please view the LICENSE
 * file that is distributed with this source code.
 *******************************************************************************/
package eu.fiesta_iot.platform.utils.commons.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyManagement {

	final static Logger log =
	        LoggerFactory.getLogger(PropertyManagement.class);

//	/**
//	 * The properties file.
//	 */
//	private static final String PROPERTIES_FILE = "fiesta-iot.properties";

	/**
	 * The properties
	 */
	private Properties props = null;

	/**
	 * The constructor
	 */
	public PropertyManagement(String propertiesFile) {
		initializeProperties(propertiesFile);
	}

	/**
	 * Initialize the Properties
	 */
	private void initializeProperties(String filename) {

		props = new Properties();
		
		// Load default properties file
		try {
			// TODO: Change for ServletContext and analyze implications
			props.load(Thread.currentThread().getContextClassLoader()
			        .getResourceAsStream(filename));
			log.info("Configuration read from classpath file " + filename);
		} catch (IOException | NullPointerException e) {
			// TODO Handle exception
			log.error("Unable to load default properties from classpath file "
			             + filename);
//			throw e;
		}

		String wildflyServerConfigDir =
		        System.getProperty("jboss.server.config.dir");
//		wildflyServerConfigDir = "D:/02_Proyectos_Internacionales/H2020/01_FIESTA/dev/00_Code/iot-registry/conf/tlmat_dev";
		
		Path filePath =
		        Paths.get(wildflyServerConfigDir).resolve(filename);
		// filePath = filePath.resolve(PROPERTIES_FILE);
		try (InputStream fis = new FileInputStream(filePath.toString())) {
			props.load(fis);
			log.info("Configuration read from " + filePath.toString());
		} catch (IOException e) {
			log.warn("Unable to load properties file: " + filePath.toString());
			log.info("Applying default configuration from internal "
			            + filename + " file");
		}
	}

	/**
	 * Gets the requested property
	 * 
	 * @param key
	 *            The key of the property
	 * @param defaultValue
	 *            The default value for the requested key
	 * 
	 * @return the requested property
	 */
	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	/**
	 * Gets the requested property
	 * 
	 * @param key
	 *            The key of the property
	 * 
	 * @return the requested property
	 */
	public String getProperty(String key) {
		return props.getProperty(key);
	}
}