package com.yhjia.me.util;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.DiskCache;

public final class DiskCacheUtils {

	private DiskCacheUtils() {
	}

	/** Returns {@link File} of cached image or <b>null</b> if image was not cached in disk cache */
	public static File findInCache(String imageUri, DiskCache diskCache) {
		File image = diskCache.get(imageUri);
		return image != null && image.exists() ? image : null;
	}

	/**
	 * Removed cached image file from disk cache (if image was cached in disk cache before)
	 *
	 * @return <b>true</b> - if cached image file existed and was deleted; <b>false</b> - otherwise.
	 */
	public static boolean removeFromCache(String imageUri, DiskCache diskCache) {
		File image = diskCache.get(imageUri);
		return image != null && image.exists() && image.delete();
	}
}
