package com.yhjia.me.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhjia.me.R;

/**
 * ��������ΪImageView�Ĳ��ַ�װ
 * ����������
 * @author ������
 *
 */
public class TopImageLayoutView extends FrameLayout{

	private View view;
	private ImageView leftBtn,rightBtn;
	private TextView topText;
	private View layoutBg;
	
	public TopImageLayoutView(Context context) {
		super(context);
	}
	
	
	public TopImageLayoutView(Context context, AttributeSet attrs) {
		super(context,attrs);
		initViews();
	}


	private void initViews() {
		view = LayoutInflater.from(getContext()).inflate(R.layout.include_top_image_layout, this, true);
		leftBtn = (ImageView) view.findViewById(R.id.leftBtn);
		topText =(TextView) view.findViewById(R.id.topText);
		rightBtn = (ImageView) view.findViewById(R.id.rightBtn);
		layoutBg = view.findViewById(R.id.imageBg);
		
 	}
	
	/**
	 * ���ñ��� 
	 */
	public void setTopText(String text){
		topText.setText(text);
	}
	
	public void setTopTextColor(int color) {
		topText.setTextColor(color);
	}
	
	/**
	 * ���ñ��� 
	 */
	public void setTopText(int id){
		topText.setText(getResources().getString(id));
	}

	/**
	 * ��������������ť�ĵ������
	 */
	public void setOnClickListener(OnClickListener clickListener){
		leftBtn.setOnClickListener(clickListener);
		rightBtn.setOnClickListener(clickListener);
	}
	
	/**
	 * �����ұ߲�����ť��ͼƬ
	 */
	public void setRightIcon(int id){
		rightBtn.setImageResource(id);
		rightBtn.setVisibility(View.VISIBLE);
	}
	
	/**
	 * ������߰�ť��ͼƬ
	 */
	public void setLeftIcon(int id){
		leftBtn.setImageResource(id);
	}
	
	/**
	 * ���������ͼ�Ƿ�ɼ�
	 * @param visibility
	 */
	public void setLeftVisibility(int visibility) {
		leftBtn.setVisibility(visibility);
	}
	
	/**
	 * �����Ҳ���ͼ�Ƿ�ɼ�
	 * @param visibility
	 */
	public void setRightVisibility(int visibility) {
		rightBtn.setVisibility(visibility);
	}
	
	/**
	 * ��ȡ�Ҳఴť����
	 * @return
	 */
	public ImageView getRightBtn () {
		return rightBtn;
	}
	
	public void setBgColor(int bgColor) {
		layoutBg.setBackgroundColor(bgColor);
	}
	
}
