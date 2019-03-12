package com.bs.job.service.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UnidUtil {

	private static int index = 1000;

	public static String getId() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "").toUpperCase();
	}

	public static synchronized String getDateId() {
		if (index > 9999) {
			index = 1000;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return simpleDateFormat.format(new Date()) + (index++);
	}
}
