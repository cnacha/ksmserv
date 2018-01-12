package com.mfu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

	public String passwordToMD5(String password) {
		MessageDigest digester;
		String md5Password = password;

		try {
			digester = MessageDigest.getInstance("MD5");
			digester.update(md5Password.getBytes());
			byte[] hash = digester.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}

			md5Password = hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return md5Password;
	}

}
