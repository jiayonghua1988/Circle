package com.yhjia.me.photo.take;

import java.util.List;

/**
 * Created by jiayonghua on 16/6/22.
 */
public interface PictureCallbackListener {
    void callback(String uri);
    void callback(List<String> uris);
}
