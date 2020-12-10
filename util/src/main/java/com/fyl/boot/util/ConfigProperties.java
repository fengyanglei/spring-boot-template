package com.fyl.boot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConfigProperties implements Serializable {
    private static final long serialVersionUID = -2498330406957672118L;
    private static final String DEFAULT_PROPERTY_FILE = "application";
    private Properties properties = null;
    private static Map<String, ConfigProperties> instanceMap = new HashMap();
    private String propertyFileName;
    private static final Logger LOG = LoggerFactory.getLogger(ConfigProperties.class);

    protected ConfigProperties(String propertyFileName) {
        this.propertyFileName = propertyFileName;
        this.loadProperties();
    }

    public static ConfigProperties getInstance() {
        return getInstance(DEFAULT_PROPERTY_FILE);
    }

    public static ConfigProperties getInstance(String propertyFileName) {
        if (instanceMap.get(propertyFileName) != null) {
            return instanceMap.get(propertyFileName);
        } else {
            ConfigProperties instance = new ConfigProperties(propertyFileName);
            instanceMap.put(propertyFileName, instance);
            return instance;
        }
    }

    public String getPropertyAsString(String propertyName, String defaultValue) {
        try {
            if (this.properties == null) {
                this.loadProperties();
            }

            return this.properties.getProperty(propertyName, defaultValue);
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public String getPropertyAsString(String propertyName) {
        return this.getPropertyAsString(propertyName, null);
    }

    public int getPropertyAsInt(String propertyName, int defaultValue) {
        try {
            if (this.properties == null) {
                this.loadProperties();
            }

            String sProperty = this.properties.getProperty(propertyName);
            return Integer.parseInt(sProperty);
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public int getPropertyAsInt(String propertyName) {
        return this.getPropertyAsInt(propertyName, 0);
    }


    public Set<Map.Entry<Object, Object>> listProperty() {
        try {
            if (this.properties == null) {
                this.loadProperties();
            }
            return this.properties.entrySet();
        } catch (Exception var4) {
            return null;
        }
    }

    protected void loadProperties() {
        try {
            this.properties = new Properties();
            ClassLoader oClassLoader = Thread.currentThread().getContextClassLoader();
            InputStream is = oClassLoader.getResourceAsStream(this.propertyFileName + ".properties");
            if (is != null) {
                this.properties.load(is);
                is.close();
            }

            is = null;
        } catch (Exception var3) {
            LOG.error(var3.getMessage(), var3);
        }

    }
}