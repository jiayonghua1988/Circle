package com.yhjia.me.gallery;

/**
 * Created by jiayonghua on 16/7/11.
 */
public class TopGalleryBean {
    private String icon;
    private String sourceid;
    private int type;
    private String userid;

    public TopGalleryBean(String icon) {
        this.icon = icon;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Class getClazz() {
        return null;
    }

    public String getIntentParamKey() {

        return "";
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getSourceid() {
        return sourceid;
    }
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
