package com.bs.job.service.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class LogUtil {

	public static void e(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter, true));
		String msg = stringWriter.toString();
		Logger.getRootLogger().error(msg);
		System.out.println(msg);
	}

}
