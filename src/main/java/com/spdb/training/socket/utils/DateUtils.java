package com.spdb.training.socket.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月21日 下午1:14:40 
*   类说明:
*/
public class DateUtils {

	public static String getDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
		return simpleDateFormat.format(new Date());		
	}
	
	public static String getCurrTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
		return simpleDateFormat.format(new Date());	
	}
	
	public static void main(String[] args) {
		
		System.out.println("time:"+getCurrTime());
	}
}
