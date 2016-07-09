package yhjia.com.circle.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yhjia.com.circle.activity.R;

/**
 * Created by jiayonghua on 16/7/9.
 */
public abstract class Fragment1Base extends Fragment{
    private TextView label;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_fragment1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        label = (TextView) view.findViewById(R.id.label);
        label.setText(initLabel());
    }

    protected abstract String initLabel();
}
