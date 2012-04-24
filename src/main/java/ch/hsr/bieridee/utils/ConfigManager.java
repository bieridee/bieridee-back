package ch.hsr.bieridee.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * ConfigManger providing settings listed in the conf.properties file.
 * 
 */
public final class ConfigManager {

	private static Properties CONFIGPROPERTY = new Properties();

	private static ConfigManager CONFIG_MANAGER_SINGLETON = new ConfigManager();

	private final static String FILENAME = "conf/conf.properties";

	private ConfigManager() {
		ConfigManager.CONFIGPROPERTY = new Properties();

		try {
			ConfigManager.CONFIGPROPERTY.load(new BufferedInputStream(new FileInputStream(ConfigManager.FILENAME)));
		} catch (IOException e) {
			System.err.println("Exception while reading config File - using default properties");
		}

	}

	/**
	 * @param property
	 *            Propertyname in the conf/conf.properties file.
	 * @param defProperty
	 *            default Property Value.
	 * @return String containing the Property value.
	 */
	public String getStringProperty(String property, String defProperty) {
		return ConfigManager.CONFIGPROPERTY.getProperty(property, defProperty);
	}

	/**
	 * @param property
	 *            String describing the value
	 * @param defProperty
	 *            Fallback default value
	 * @return integer value
	 */
	public int getIntProperty(String property, int defProperty) {
		return Integer.parseInt(ConfigManager.CONFIGPROPERTY.getProperty(property, Integer.toString(defProperty)));
	}

	/**
	 * @param key
	 *            Identifier for the Config Value
	 * @param value
	 *            Value to save
	 */
	public void setProperty(String key, String value) {
		ConfigManager.CONFIGPROPERTY.setProperty(key, value);
	}

	/**
	 * @param key
	 *            Identifier for the Config Value
	 * @param value
	 *            Value to save
	 */
	public void setProperty(String key, int value) {
		this.setProperty(key, Integer.toString(value));
	}

	/**
	 * Saves to whole config to file.
	 */
	public void saveConfig() {
		try {
			final OutputStream out = new FileOutputStream(new File(ConfigManager.FILENAME));
			ConfigManager.CONFIGPROPERTY.store(out, "Config Saved");

		} catch (IOException e) {
			System.out.println("Failure writting the config file");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @return the ConfigManager Singleton
	 */
	public static ConfigManager getManager() {
		return ConfigManager.CONFIG_MANAGER_SINGLETON;
	}
}
