package yhjia.com.circle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yhjia.me.adapter.FBaseAdapter;

import java.util.List;

import yhjia.com.circle.activity.R;

/**
 * Created e on 16/7/21.
 * @author jiayonghua
 */
public class ListPopupWindowAdapter extends FBaseAdapter<String>{


    public ListPopupWindowAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public int settingDefaultPicture() {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_popup,null);
            holderView = new HolderView();
            holderView.tvContent1 = (TextView) convertView.findViewById(R.id.tvLabel);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        holderView.tvContent1.setText(list.get(position));
        return convertView;
    }

}
