package com.monkey.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class MD5Parse {
	public static String parseStrToMd5L16(String paramString) {
		String str = parseStrToMd5L32(paramString);
		if (str != null)
			str = str.substring(8, 24);
		return str;
	}

	public static String parseStrToMd5L32(String str) {
		try {
			byte[] arr = MessageDigest.getInstance("MD5")
					.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : arr) {
				int k = 0xFF & b;
				if (k < 16)
					sb.append(0);
				sb.append(Integer.toHexString(k));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parseStrToMd5U16(String param) {
		String str = parseStrToMd5L32(param);
		if (str != null)
			str = str.toUpperCase().substring(8, 24);
		return str;
	}

	public static String parseStrToMd5U32(String param) {
		String str = parseStrToMd5L32(param);
		if (str != null)
			str = str.toUpperCase();
		return str;
	}
	
}