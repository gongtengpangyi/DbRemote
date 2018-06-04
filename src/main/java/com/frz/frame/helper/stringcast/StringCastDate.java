package com.frz.frame.helper.stringcast;

import com.frz.frame.helper.ReflectHelper.StringCast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringCastDate implements StringCast {
	/**
	 * 默认的时间格式
	 */
	public static final String defaultDateFormatStr = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 单例模式实现
	 */
	private static StringCastDate instance;

	public static StringCastDate newInstance(String formatStr) {
		if (instance == null) {
			synchronized (StringCastDate.class) {
				instance = new StringCastDate(formatStr);
			}
		} else {
			instance.setFormat(formatStr);
		}
		return instance;
	}

	private SimpleDateFormat format;

	protected StringCastDate(String formatStr) {
		super();
		format = new SimpleDateFormat(formatStr);
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	/**
	 * 重新赋值时间格式
	 * 
	 * @param formatStr
	 *            时间格式
	 */
	public void setFormat(String formatStr) {
		this.format = new SimpleDateFormat(formatStr);
	}

	@Override
	public Object cast(String value) {
		try {
			return format.parse(value);
		} catch (ParseException e) {
			System.out.println("\nStringCastDate.cast强制转换出错\n");
			e.printStackTrace();
		}
		return null;
	}

}
