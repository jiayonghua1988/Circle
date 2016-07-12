package yhjia.com.circle.bean;

/**
 * Created by jiayonghua on 16/7/12.
 */
public class PinnerSectionListBean {
    private int type;
    private String label;

    public PinnerSectionListBean(int type,String label) {
        this.type = type;
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
