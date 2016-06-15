package com.yhjia.me.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

/**
 * ����
 * @author yhjia
 *
 */
public class ShareUtil {
	public static void wxShare(ArrayList<Uri> uris,Activity activity ) {
		  Intent intent = new Intent();
		  // ���?����Ȧ
		ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");
		intent.setComponent(comp);
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		activity.startActivity(intent);
	}
}
