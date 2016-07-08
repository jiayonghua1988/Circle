package com.chiang.framework.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yhjia.me.R;


public class PullToRefreshListView extends ListView implements OnScrollListener {

	private static final String TAG = "listview";

	private final static int RATIO = 3;

	private LayoutInflater inflater;
	private LinearLayout headView;

	private TextView tipsTextview;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	private boolean isRecored;

	private int headContentWidth;
	private int headContentHeight;
	private int startY;
	private int firstItemIndex;

	private State state = State.RELEASE_To_REFRESH ;
	private boolean isBack;

	private OnRefreshListener refreshListener;

	private boolean isPullDown = true;
	private boolean isRefreshMore = true;

	private TextView footerTipsTextView;
	private View footerProgressBar;
	private View footerLayout;
	
	public enum State {
		RELEASE_To_REFRESH,PULL_To_REFRESH,UP_REFRESHING,DOWN_REFRESHING,DONE
	}
	

	public PullToRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint({ "InlinedApi", "NewApi" })
	private void init(Context context) {
		if(Build.VERSION.SDK_INT>8){
			setOverScrollMode(View. OVER_SCROLL_NEVER);	
		}
		
		setCacheColorHint(context.getResources().getColor(
				android.R.color.transparent));
		inflater = LayoutInflater.from(context);

		headView = (LinearLayout) inflater.inflate(R.layout.pull_to_refresh_header, null);

		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
//		arrowImageView.setMinimumWidth(70);
//		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		Log.v("size", "width:" + headContentWidth + " height:" + headContentHeight);

		addHeaderView(headView, null, false);
		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		
		//添加footerView
		View inflateFooterLayou = inflater.inflate(R.layout.pull_to_refresh_footer, null);
		footerLayout = inflateFooterLayou.findViewById(R.id.mPullFooterView);
		footerTipsTextView = (TextView) inflateFooterLayou.findViewById(R.id.footerTipsTextView);
		footerProgressBar = inflateFooterLayou.findViewById(R.id.footerProgressBar);
		addFooterView(inflateFooterLayou, null, false);

		state = State.DONE;
		setRefreshMore(true);
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem, int visibleItemCount,int totalItemCount) {
		firstItemIndex = firstVisiableItem;
		if(refreshListener!=null && isRefreshMore){
			if((firstVisiableItem+visibleItemCount) == totalItemCount 
					&& visibleItemCount!=totalItemCount 
					&& loadingState()){
				state=State.UP_REFRESHING;
				refreshListener.onPullUpRefresh();
			}
		}
	}
	
	private boolean loadingState(){
		return state!=State.UP_REFRESHING && state!=State.DOWN_REFRESHING;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		
	}

	public boolean onTouchEvent(MotionEvent event) {

		firstItemIndex = getFirstVisiblePosition();

		if (isPullDown) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
					Log.v(TAG, "在down时候记录当前位置‘");
				}
				break;

			case MotionEvent.ACTION_UP:

				if (loadingState()) {
					
					if (state == State.PULL_To_REFRESH) {
						state = State.DONE;
						switchoverHeaderView();

						Log.v(TAG, "由下拉刷新状态，到done状态");
					}
					if (state == State.RELEASE_To_REFRESH) {
						state = State.DOWN_REFRESHING;
						switchoverHeaderView();
						
						if(refreshListener!=null){
							refreshListener.onPullDownRefresh();
						}

						Log.v(TAG, "由松开刷新状态，到done状态");
					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				
				if (loadingState() && isRecored) {
					if (state == State.RELEASE_To_REFRESH) {
						setSelection(0);

						if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0) {
							state = State.PULL_To_REFRESH;
							switchoverHeaderView();

							Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
						}
						else if (tempY - startY <= 0) {
							state = State.DONE;
							switchoverHeaderView();

							Log.v(TAG, "由松开刷新状态转变到done状态");
						}
						else {
						}
					}
					if (state == State.PULL_To_REFRESH) {

						setSelection(0);

						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = State.RELEASE_To_REFRESH;
							isBack = true;
							switchoverHeaderView();

							Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
						}
						else if (tempY - startY <= 0) {
							state = State.DONE;
							switchoverHeaderView();

							Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
						}
					}

					if (state == State.DONE) {
						if (tempY - startY > 0) {
							state = State.PULL_To_REFRESH;
							switchoverHeaderView();
						}
					}

					if (state == State.PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);

					}

					if (state == State.RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}

				}

				break;
			}
		}

		return super.onTouchEvent(event);
	}

	public void switchoverHeaderView() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText(R.string.pull_release_refresh);

			Log.v(TAG, "当前状态，松开刷新");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
			}
			tipsTextview.setText(R.string.pull_down_refresh);
			Log.v(TAG, "当前状态，下拉刷新");
			break;

		case DOWN_REFRESHING:

			headView.setPadding(0, 0, 0, 0);

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(R.string.loading);

			Log.v(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			tipsTextview.setText(R.string.pull_down_refresh);

			Log.v(TAG, "当前状态，done");
			break;
		default:
			break;
		}
	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}
	
	public interface OnRefreshListener {
		public void onPullUpRefresh();
		public void onPullDownRefresh();
	}

	public void onRefreshComplete() {
		if(state == State.UP_REFRESHING){
			state = State.DONE;
			
		}else if(state == State.DOWN_REFRESHING){
			state = State.DONE;
			switchoverHeaderView();
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	public void setRefreshMore(boolean isRefreshMore) {
		this.isRefreshMore = isRefreshMore;
		if(!isRefreshMore){
			footerTipsTextView.setText(R.string.NoMore);
			footerProgressBar.setVisibility(View.GONE);
		}else{
			footerTipsTextView.setText(R.string.loading);
			footerProgressBar.setVisibility(View.VISIBLE);
		}
		
		if(getAdapter()==null || getAdapter().getCount()<10){
			footerLayout.setVisibility(View.GONE);
		}else{
			footerLayout.setVisibility(View.VISIBLE);
		}
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isPullDown() {
		return isPullDown;
	}

	public void setPullDown(boolean isPullDown) {
		this.isPullDown = isPullDown;
	}
	
}
