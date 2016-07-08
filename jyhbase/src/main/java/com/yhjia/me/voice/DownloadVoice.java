package com.yhjia.me.voice;

import android.os.AsyncTask;

import com.yhjia.me.db.Config;
import com.yhjia.me.util.FileUtil;

import java.util.ArrayList;

public class DownloadVoice extends AsyncTask<Void, String, Void> {

	private static boolean downloading = false;
	private IDownload download;
	private static final ArrayList<String> urls = new ArrayList<String>();
	private static DownloadVoice downloadVoice;

	public void setDownload(IDownload download) {
		this.download = download;
	}

	public static void addVoiceUrl(String url) {
		if (!urls.contains(url)) {
			urls.add(url);
		}
	}

	public static void startDownload() {
		if (!downloading) {
			downloadVoice = new DownloadVoice();
			downloadVoice.execute();
		}
	}

	public static void startDownload(IDownload download) {

		if (!downloading || downloadVoice == null) {
			downloadVoice = new DownloadVoice();
			downloadVoice.execute();
		}
		downloadVoice.setDownload(download);
	}

	@Override
	protected Void doInBackground(Void... params) {
		downloading = true;
		while (!urls.isEmpty()) {
			String name = FileUtil.downloadFile2SD(urls.get(0), Config.CACHE_VOICE_PATH);
			publishProgress(name);
			
			urls.remove(0);
		}
		downloading = false;
		return null;
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		if (download != null) {
			download.finish(Config.CACHE_VOICE_PATH + values[0]);
		}
	}

	public interface IDownload {
		public void finish(String filePath);
	}

}