package com.yhjia.me.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class StreamConverts {

	public static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");
	    BufferedReader br =new BufferedReader(reader);
	    
	    String str = null;
	    StringBuilder builder=new StringBuilder();
	    while((str = br.readLine())!=null){
	    	builder.append(str);
	    }
	    return builder.toString().trim();
	}
	
	
}