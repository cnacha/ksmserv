package com.mfu.util;

import java.security.SecureRandom;

public class StringUtil {
	static final String AB = "abcdefghijklmnopqrstuvwxyz";
	static final String NUMBER = "0123456789";
	static SecureRandom rnd = new SecureRandom();

	public static String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	public static String randomNumber( int len ){
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( NUMBER.charAt( rnd.nextInt(NUMBER.length()) ) );
		   return sb.toString();
		}
}

