package com.yhjia.me.util;

import com.yhjia.me.app.IApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DebugUtil
{
	public static final boolean ISTEST = true;
	public static String getResponse(String path) {
		try
		{
			InputStream is = IApplication.getInstance().getApplicationContext().getResources().getAssets().open(path);
			return new String(InputStreamToByte(is));
		}
		catch (IOException e)
		{
			
		}
		return null;
	}
	private static byte[] InputStreamToByte(InputStream is) throws IOException
	{
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1)
		{
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
}