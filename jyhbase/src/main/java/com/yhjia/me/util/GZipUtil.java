package com.yhjia.me.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {

	private static final int BUFFER = 2*1024;
	
	public static String decompressGZip(InputStream is)throws IOException {
		InputStream inputStream = null;
		BufferedInputStream bis = new BufferedInputStream(is);
		try {
			bis.mark(2);
			byte[] header = new byte[2];
			int result = bis.read(header);
			bis.reset();
			int headerData =(int)((header[0]<<8) | header[1]&0xFF);
			if (result != -1 && headerData == 0x1f8b) {
				inputStream = new GZIPInputStream(bis);
			} else {
				inputStream = bis;
			}
			return StreamConverts.readIt(inputStream);
		}finally{
			if(bis!=null){
				bis.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
		}
		
	}
	
	public static void compress(InputStream is, OutputStream os)
			throws Exception {

		GZIPOutputStream gos = new GZIPOutputStream(os);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}

		gos.finish();

		gos.flush();
		gos.close();
	}

	public static void decompress(InputStream is, OutputStream os)
			throws Exception {

		GZIPInputStream gis = new GZIPInputStream(is);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}

		gis.close();
	}

}