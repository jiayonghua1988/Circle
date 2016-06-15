package com.yhjia.me.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhjia.me.R;


/**
 * ͨ�ö������ַ�װ
 * ���������� 
 * @author ������
 * 
 */
public class TopLayoutView extends FrameLayout {

	private View view;
	private ImageView leftBtn;
	private TextView topText, rightBtn;
	private View topBg, line;

	public TopLayoutView(Context context) {
		super(context);
	}

	public TopLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	private void initViews() {
		view = LayoutInflater.from(getContext()).inflate(R.layout.include_top_layout, this, true);
		leftBtn = (ImageView) view.findViewById(R.id.leftBtn);
		topText = (TextView) view.findViewById(R.id.topText);
		line = view.findViewById(R.id.line);
		rightBtn = (TextView) view.findViewById(R.id.rightBtn);
		topBg = view.findViewById(R.id.topBg);

	}

	/**
	 * ��������������ť�ĵ������
	 */
	public void setOnClickListener(OnClickListener clickListener) {
		leftBtn.setOnClickListener(clickListener);
		rightBtn.setOnClickListener(clickListener);
	}

	/**
	 * ���������ͼ�Ƿ�ɼ�
	 * 
	 * @param visibility
	 */
	public void setLeftVisibility(int visibility) {
		leftBtn.setVisibility(visibility);
	}

	public void setLeftImage(int drawableId) {
		if (drawableId > 0) {
			leftBtn.setImageResource(drawableId);
		}
	}

	/**
	 * �����Ҳ���ͼ�Ƿ�ɼ�
	 * 
	 * @param visibility
	 */
	public void setRightVisibility(int visibility) {
		rightBtn.setVisibility(visibility);
	}

	/**
	 * �����Ҳ౳��
	 * 
	 * @param resId
	 */
	public void setRightBacgroundResource(int resId) {
		rightBtn.setBackgroundResource(resId);
	}

	/**
	 * ��ȡ�Ҳఴť����
	 * 
	 * @return
	 */
	public TextView getRightBtn() {
		return rightBtn;
	}

	/**
	 * ���ñ����������ɫ
	 * 
	 * @param color
	 */
	public void setTopTextColor(String color) {
		if (!TextUtils.isEmpty(color)) {
			topText.setTextColor(Color.parseColor(color));
		}
	}

	/**
	 * �����ұ߰�ť��������ɫ
	 * 
	 * @param color
	 */
	public void setRightTextColor(String color) {
		if (!TextUtils.isEmpty(color)) {
			rightBtn.setTextColor(Color.parseColor(color));
		}
	}

	public void setTopLayoutBg(String color) {
		if (!TextUtils.isEmpty(color)) {
			topBg.setBackgroundColor(Color.parseColor(color));
		}
	}
	
	/**
	 * 
	 * @param type  
	 * ONLY_TITLE ����ʾ����     
	 * BACK_AND_TITLE  ��ʾ���غͱ��� 
	 * ALL   ��ʾ���� ����  ���ұߵı༭��ť
	 * @param labels  ��һ��string�����Ǳ���   �ڶ���string�������ұ߰�ť���İ�
	 */
	public void setCommonTopLayout(Type type,String...  labels) {
		topText.setVisibility(View.VISIBLE);
		switch (type) {
		case ONLY_TITLE:
			setLeftVisibility(View.GONE);
			setRightVisibility(View.GONE);
			if (labels != null && labels.length > 0) {
				topText.setText(labels[0]);
			}
			break;
		case BACK_AND_TITLE:
			setLeftVisibility(View.VISIBLE);
			setRightVisibility(View.GONE);
			if (labels != null && labels.length > 0) {
				topText.setText(labels[0]);
			}
			break;
			
		case ALL:
			setLeftVisibility(View.VISIBLE);
			setRightVisibility(View.VISIBLE);
			if (labels != null && labels.length > 1) {
				topText.setText(labels[0]);
				rightBtn.setText(labels[1]);
			}
			break;
		case TITLE_AND_RIGHT:
			setLeftVisibility(View.GONE);
			setRightVisibility(View.VISIBLE);
			if (labels != null && labels.length > 1) {
				topText.setText(labels[0]);
				rightBtn.setText(labels[1]);
			}
			break;
		}
	}
	
	public enum Type {
		ONLY_TITLE,BACK_AND_TITLE,ALL,TITLE_AND_RIGHT
	}

}