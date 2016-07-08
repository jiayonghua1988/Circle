package com.yhjia.me.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yhjia.me.R;


/**
 * 加载更多
 * @author Aaren
 *
 */
public class MoreListView extends ListView implements OnScrollListener {

	private OnMoreRefresh moreRefresh;

	private boolean isRefreshMore;

	private LayoutInflater mInflater;
	private View mFooterView, inflateFooterLayout;
	private TextView mFooterTextView;
	private ProgressBar mFooterProgressBar;

	public MoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	public MoreListView(Context context) {
		super(context);
		initViews();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(getContext());

		inflateFooterLayout = mInflater.inflate(R.layout.pull_to_refresh_footer, this , false);
		mFooterView = inflateFooterLayout.findViewById(R.id.mPullFooterView);
		mFooterTextView = (TextView) inflateFooterLayout.findViewById(R.id.footerTipsTextView);
		mFooterProgressBar = (ProgressBar) inflateFooterLayout.findViewById(R.id.footerProgressBar);
		mFooterView.setVisibility(View.GONE);
		addFooterView(inflateFooterLayout);

		setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
		if (moreRefresh != null) {
			if ((firstVisiableItem + visibleItemCount) == totalItemCount && visibleItemCount != totalItemCount && canLoadMore()) {
				isRefreshMore = false;
				moreRefresh.loadMore();
			}
		}
	}

	private boolean canLoadMore() {
		return isRefreshMore;
	}

	public void setRefreshMore(boolean isRefreshMore) {
		this.isRefreshMore = isRefreshMore;
		if (!isRefreshMore) {
			mFooterTextView.setText(R.string.NoMore);
			mFooterProgressBar.setVisibility(View.GONE);
		} else {
			mFooterTextView.setText(R.string.loading);
			mFooterProgressBar.setVisibility(View.VISIBLE);
		}

		if (getAdapter() == null || getAdapter().getCount() < 10) {
			mFooterView.setVisibility(View.GONE);
		} else {
			mFooterView.setVisibility(View.VISIBLE);
		}
	}

	public interface OnMoreRefresh {
		public void loadMore();
	}

	public void setOnMoreRefresh(OnMoreRefresh moreRefresh) {
		this.moreRefresh = moreRefresh;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

}