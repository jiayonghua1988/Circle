package yhjia.com.circle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yhjia.me.adapter.YYBaseAdapter;

import java.util.List;

import yhjia.com.circle.activity.R;
import yhjia.com.circle.bean.MainItemClick;

/**
 * Created by jiayonghua on 16/7/12.
 */
public class MainAdapter extends YYBaseAdapter<String>{
    private MainItemClick mainItemClick;

    public MainAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public int settingDefaultPicture() {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_main,null);
            holderView = new HolderView();
            holderView.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        holderView.name.setText(list.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainItemClick != null) {
                    mainItemClick.onItem(position);
                }
            }
        });
        return convertView;
    }

    public class HolderView {
        public TextView name;
    }

    public void setMainItemClickListener(MainItemClick mainItemClick) {
        this.mainItemClick = mainItemClick;
    }
}
