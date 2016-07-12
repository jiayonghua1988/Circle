package yhjia.com.circle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yhjia.me.listview.PinnerSectionAdapter;

import java.util.List;

import yhjia.com.circle.activity.R;
import yhjia.com.circle.bean.PinnerSectionListBean;

/**
 * Created by jiayonghua on 16/7/12.
 */
public class PinnerSectionListAdapter extends PinnerSectionAdapter<PinnerSectionListBean>{
    private LayoutInflater inflater;

    public PinnerSectionListAdapter(Context context, List<PinnerSectionListBean> list) {
        super(context, list);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getHolderItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public View getHolderView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = inflater.inflate(R.layout.item_pinner_section,null);
            holderView.titleView = (TextView) convertView.findViewById(R.id.titleView);
            holderView.contentView = (TextView) convertView.findViewById(R.id.contentView);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        PinnerSectionListBean pinnerSectionListBean = list.get(position);
        String label = pinnerSectionListBean.getLabel();
        if (pinnerSectionListBean.getType() == 1) {
            holderView.titleView.setVisibility(View.VISIBLE);
            holderView.titleView.setText(label);
            holderView.contentView.setVisibility(View.GONE);
        } else {
            holderView.contentView.setVisibility(View.VISIBLE);
            holderView.contentView.setText(label);
            holderView.titleView.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class HolderView {
        public TextView titleView;
        public TextView contentView;
    }
}
