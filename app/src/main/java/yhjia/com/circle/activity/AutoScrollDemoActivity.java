package yhjia.com.circle.activity;

import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.gallery.AutoScrollPagerView;
import com.yhjia.me.gallery.TopGalleryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayonghua on 16/7/11.
 * banner 自动轮播
 */
public class AutoScrollDemoActivity extends BaseActivity{
    private AutoScrollPagerView gallery;
    @Override
    public int initLayoutID() {
        return R.layout.activity_auto_scroll_demo;
    }

    @Override
    public void initView() {
        gallery = findView(R.id.gallery);
    }

    @Override
    public void initLogic() {
        List<TopGalleryBean> list = new ArrayList<TopGalleryBean>();
        list.add(new TopGalleryBean("http://pic50.nipic.com/file/20141021/14567310_105242655734_2.jpg"));
        list.add(new TopGalleryBean("http://img3.imgtn.bdimg.com/it/u=1530482895,1504808046&fm=21&gp=0.jpg"));
        list.add(new TopGalleryBean("http://img2.imgtn.bdimg.com/it/u=395920684,863299018&fm=21&gp=0.jpg"));
        list.add(new TopGalleryBean("http://img5.imgtn.bdimg.com/it/u=3272404417,2720683928&fm=21&gp=0.jpg"));
        list.add(new TopGalleryBean("http://img2.imgtn.bdimg.com/it/u=395920684,863299018&fm=21&gp=0.jpg"));
        list.add(new TopGalleryBean("http://img3.imgtn.bdimg.com/it/u=1530482895,1504808046&fm=21&gp=0.jpg"));
        gallery.setTopGalleryData(list,this);
    }
}
