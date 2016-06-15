package com.yhjia.me.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	/**
	 * ��ȡ��ǰʱ�䲢��ʽ��
	 * 
	 */
	public static String getCurTime() {
		long CurTime = System.currentTimeMillis();
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return fomat.format(new Date(CurTime));
	}

	/**
	 * ʱ���ת��Ϊ�ַ�
	 */
	public static String[] getStrTime(String curr) {
		String re_Str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long lcc_time = Long.valueOf(curr);
		re_Str = sdf.format(new Date(lcc_time * 1000L));
		String[] strings = re_Str.split("-");
		return strings;
	}

	/**
	 * ��������
	 */
	public static String getNumber(String count) {
		String number = null;
		switch (Integer.parseInt(count)) {
		case 01:
			number = "һ";
			break;
		case 02:
			number = "��";
			break;
		case 03:
			number = "��";
			break;
		case 04:
			number = "��";
			break;
		case 05:
			number = "��";
			break;
		case 06:
			number = "��";
			break;
		case 07:
			number = "��";
			break;
		case 10:
			number = "ʮ";
			break;
		case 11:
			number = "ʮһ";
			break;
		case 12:
			number = "ʮ��";
			break;
		default:
				if(count.equals("08")){
					number = "��";
				}else if(count.equals("09")) {
					number = "��";
				}
			
			break;
		}
		return number;
		 
	}

}
