package com.ai.runner.center.dshm.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public final class Logger {
	private Logger(){
		
	}
	public static final Log log = LogFactory.getLog(Logger.class);
	
	public static final Log getLogger(Class clazz)
	{
		return LogFactory.getLog(clazz);
	}
}
