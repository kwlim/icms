package com.lkwy.web;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebappServletContext implements ServletContextListener{
	
	static final Logger LOGGER = LoggerFactory.getLogger(WebappServletContext.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.debug("WebappServletContext|contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.debug("WebappServletContext|contextDestroyed");
		Enumeration<Driver> drivers = DriverManager.getDrivers();
	    while (drivers.hasMoreElements()) {
	        Driver driver = drivers.nextElement();
	        ClassLoader driverclassLoader = driver.getClass().getClassLoader();
	        ClassLoader thisClassLoader = this.getClass().getClassLoader();
	        if (driverclassLoader != null && thisClassLoader != null &&  driverclassLoader.equals(thisClassLoader)) {
	            try {
	            	LOGGER.debug("WebappServletContext|DeregisterDriver|{}", driver);
	                DriverManager.deregisterDriver(driver);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
}