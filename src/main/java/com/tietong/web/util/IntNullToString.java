package com.tietong.web.util;

public class IntNullToString {
	public static int trans(String s){
		if(s == null){
			return 0;
		}
		else{
			return Integer.parseInt(s);
		}
		
	}
}
