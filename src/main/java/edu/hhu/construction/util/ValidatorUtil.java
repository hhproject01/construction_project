package edu.hhu.construction.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断手机号格式
 */
public class ValidatorUtil {

	//以1开头 后面加10个数字 就认为是正确的手机号
	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	public static boolean isMobile(String src) {
		if(StringUtils.isEmpty(src)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(src);
		return m.matches();
	}
	
	public static void main(String[] args) {
			System.out.println(isMobile("18912341234"));
			System.out.println(isMobile("1891234123"));
	}
}
