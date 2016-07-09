package com.yhjia.me.bean;

import java.io.Serializable;

/**
 * Created by jiayonghua on 16/7/9.
 */
public class MessagePicture implements Serializable {
    private String picUrl;

    public MessagePicture(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
