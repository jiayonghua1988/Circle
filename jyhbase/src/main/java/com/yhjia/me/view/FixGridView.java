package com.chiang.framework.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
public class FixGridView extends GridView {

	public FixGridView(Context context) {
		super(context);
	}

	public FixGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override  
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	    int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	            MeasureSpec.AT_MOST);  
	    super.onMeasure(widthMeasureSpec, expandSpec);  
	  
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	public interface OnTouchBlankPositionListener {
		
		boolean onTouchBlankPosition();
	}

	private OnTouchBlankPositionListener mTouchBlankPosListener;
	public void setOnTouchBlankPositionListener(
			OnTouchBlankPositionListener listener) {
		mTouchBlankPosListener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mTouchBlankPosListener != null) {
			if (!isEnabled()) {
				// A disabled view that is clickable still consumes the touch
				// events, it just doesn't respond to them.
				return isClickable() || isLongClickable();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				final int motionPosition = pointToPosition((int) event.getX(), (int) event.getY());
				if (motionPosition == INVALID_POSITION) {
					return mTouchBlankPosListener.onTouchBlankPosition();
				}
			}
		}

		return super.onTouchEvent(event);
	}
}
