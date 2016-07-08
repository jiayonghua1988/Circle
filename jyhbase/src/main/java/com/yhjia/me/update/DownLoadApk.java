package com.yhjia.me.update;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.yhjia.me.util.FileUtil;
import com.yhjia.me.util.Prompt;
import com.yhjia.me.util.SystemInfo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 版本更新帮助类
 */
public class DownLoadApk {

    private static final String LOADING = "正在下载";
    private static final String COMMPLTE = "下载完成，点击安装！";
    private static final String FAIL = "下载安装包失败！";

    private String fileName = "_update.apk";
    private String filePath = Environment.getExternalStorageDirectory().getPath()+"/Android/data/";
    private static final int NOTIFICATIONID = 1024;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyMgr;
    private Context context;

    public DownLoadApk(Context context, int icon) {
        this.context = context;
        mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context).setSmallIcon(icon).setContentTitle(SystemInfo.getAppName(context)).setAutoCancel(false).setOngoing(true);
    }

    public void execute(String url) {
        execute(0, url);
    }

    public void execute(long size,String url) {
        new DownLoadApkTask(size).execute(url);
    }

    public void showUpdate(final String url,String message,OnClickListener cancelListener){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("去更新", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                execute(url);
            }
        });
        builder.setNegativeButton("以后再说", cancelListener);
        builder.show();
    }

    private class DownLoadApkTask extends AsyncTask<String, Integer, String> {
        private long size;

        public DownLoadApkTask(long size) {
            this.size = size;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(context, "通知栏更新下载...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            final String url = params[0];
            if (TextUtils.isEmpty(url)) {
                Prompt.printLog("url is null");
                return null;
            }
            String path = filePath + fileName;
            FileUtil.deleteFile(path);
            OutputStream os = null;
            try {
                HttpGet request = null;
                HttpResponse response = null;
                InputStream is = null;
                request = new HttpGet(url);
                response = new DefaultHttpClient().execute(request);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    is = response.getEntity().getContent();
                    os = new FileOutputStream(path);
                    byte[] bytes = new byte[1024];
                    int buffer = 0;

                    // 总长度
                    long length = size;
                    if(length == 0){
                        length = response.getEntity().getContentLength();
                    }

                    int nowlength = 0;
                    int notifylength = 0;
                    while ((buffer = is.read(bytes)) != -1) {
                        os.write(bytes, 0, buffer);
                        nowlength += buffer;
                        if ((int) (nowlength * 100 / length) > notifylength) {
                            notifylength = (int) (nowlength * 100 / length);
                            publishProgress(notifylength);
                        }
                        os.flush();
                    }
                }
                return path;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            notifyDownLoading(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (TextUtils.isEmpty(result)) {
                failed();
            } else {
                notifyCommplete(result);
            }
        }
    }

    private void notifyDownLoading(int progress) {
        mBuilder.setContentText(LOADING).setProgress(100, progress, false).setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT)).setContentInfo(new StringBuffer(String.valueOf(progress)).append("%").toString());
        // Displays the progress bar for the first time.
        mNotifyMgr.notify(NOTIFICATIONID, mBuilder.build());

    }

    private void notifyCommplete(String apkpath) {
//		mBuilder.setContentText(COMMPLTE).setProgress(0, 0, false).setContentInfo(null).setContentIntent(openApk(apkpath)).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND).setOngoing(false);
//
//		mNotifyMgr.notify(NOTIFICATIONID, mBuilder.build());
        mNotifyMgr.cancelAll();
        File updateFile = new File(apkpath);
        Uri uri = Uri.fromFile(updateFile);
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(installIntent);
    }

    private void failed() {
        mBuilder.setContentText(FAIL).setProgress(0, 0, false).setContentInfo(null).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND).setOngoing(false);

        mNotifyMgr.notify(NOTIFICATIONID, mBuilder.build());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private PendingIntent openApk(String apkpath) {
        try {
            File file = new File(apkpath);
            Intent resultIntent = new Intent();
//			resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            resultIntent.setAction(Intent.ACTION_VIEW);
            resultIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            return PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
