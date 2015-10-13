package com.tietong.web.util;

public class StringNullToInt {
	public static int trans(String s){
		if(s == null){
			return 0;
		}
		else{
			try{return Integer.parseInt(s);}
			catch(Exception e){
				return 0;
			}
		}
		
	}
	
	public static double transToDouble(String s){
		if(s == null){
			return 0;
		}
		else{
			try{return Double.parseDouble(s);}
			catch(Exception e){
				return 0;
			}
		}
		
	}
}
