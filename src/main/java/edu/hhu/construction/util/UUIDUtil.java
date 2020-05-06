package edu.hhu.construction.util;

import java.util.UUID;

/**
 * 生成一个UUID
 */
public class UUIDUtil {

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
