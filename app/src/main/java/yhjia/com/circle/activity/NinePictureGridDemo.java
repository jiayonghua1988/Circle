package yhjia.com.circle.activity;

import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.bean.MessagePicture;
import com.yhjia.me.view.NineGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayonghua on 16/7/9.
 * 图片显示9宫格
 */
public class NinePictureGridDemo  extends BaseActivity{
    private NineGridView nineGrid;
    @Override
    public int initLayoutID() {
        return R.layout.activity_nine_picture;
    }

    @Override
    public void initView() {
        nineGrid = findView(R.id.nineGrid);
    }

    @Override
    public void initLogic() {
        nineGrid.setActivity(this);
        List<MessagePicture> list = new ArrayList<MessagePicture>();
        list.add(new MessagePicture("http://pic50.nipic.com/file/20141021/14567310_105242655734_2.jpg"));
        list.add(new MessagePicture("http://img3.imgtn.bdimg.com/it/u=1530482895,1504808046&fm=21&gp=0.jpg"));
        list.add(new MessagePicture("http://img2.imgtn.bdimg.com/it/u=395920684,863299018&fm=21&gp=0.jpg"));
        list.add(new MessagePicture("http://img5.imgtn.bdimg.com/it/u=3272404417,2720683928&fm=21&gp=0.jpg"));
        list.add(new MessagePicture("http://img2.imgtn.bdimg.com/it/u=395920684,863299018&fm=21&gp=0.jpg"));
        list.add(new MessagePicture("http://img3.imgtn.bdimg.com/it/u=1530482895,1504808046&fm=21&gp=0.jpg"));
        list.add(new MessagePicture("http://img3.imgtn.bdimg.com/it/u=1530482895,1504808046&fm=21&gp=0.jpg"));
        list.add(new MessagePicture("http://img2.imgtn.bdimg.com/it/u=395920684,863299018&fm=21&gp=0.jpg"));
        list.add(new MessagePicture("http://img2.imgtn.bdimg.com/it/u=395920684,863299018&fm=21&gp=0.jpg"));
        nineGrid.showBigImages(list);
    }
}
