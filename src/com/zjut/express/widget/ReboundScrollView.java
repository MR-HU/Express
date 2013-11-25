package com.zjut.express.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * ���з���Ч����ScrollView
 * @date 2013-11-20 ����9:42:56
 * @modify
 * @version 1.0.0
 */
public class ReboundScrollView extends ScrollView {
	
	//����View
	private View inner;
	//���ʱy����
	private float y;
	//����(����ֻ�Ǹ���ʽ,ֻ�������ж��Ƿ���Ҫ����)
	private Rect normal = new Rect();
	//�Ƿ�ʼ����
	private boolean isCount = false;

	public ReboundScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/***
	 * ���� XML ������ͼ�������.�ú�����������ͼ��������,����������ͼ�����֮��. ��ʹ���า���� onFinishInflate
	 * ����,ҲӦ�õ��ø���ķ���,ʹ�÷�������ִ��.
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}

	/***
	 * ����touch
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {
			commOnTouchEvent(ev);
		}

		return super.onTouchEvent(ev);
	}

	/***
	 * �����¼�
	 * @param ev
	 */
	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			//��ָ�ɿ�
			if (isNeedAnimation()) {
				animation();
				isCount = false;
			}
			break;
		/***
		 * �ų�����һ���ƶ����㣬��Ϊ��һ���޷���֪y���꣬ ��MotionEvent.ACTION_DOWN�л�ȡ������
		 * ��Ϊ��ʱ��MyScrollView��touch�¼����ݵ�����LIstView�ĺ���item����.���Դӵڶ��μ��㿪ʼ.
		 * Ȼ������ҲҪ���г�ʼ�������ǵ�һ���ƶ���ʱ���û��������0. ֮���¼׼ȷ�˾�����ִ��.
		 */
		case MotionEvent.ACTION_MOVE:
			//����ʱ��y����
			final float preY = y;
			//ʵʱy����
			float nowY = ev.getY();
			//��������
			int deltaY = (int) (preY - nowY);
			if (!isCount) {
				//������Ҫ��0
				deltaY = 0; 
			}

			y = nowY;
			//�����������ϻ�������ʱ�Ͳ����ٹ�������ʱ�ƶ�����
			if (isNeedMove()) {
				//��ʼ��ͷ������
				if (normal.isEmpty()) {
					//���������Ĳ���λ��
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());
				}
				//�ƶ�����
				inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
						inner.getRight(), inner.getBottom() - deltaY / 2);
			}
			isCount = true;
			break;

		default:
			break;
		}
	}

	/***
	 * ��������
	 */
	public void animation() {
		//�����ƶ�����
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		//���ûص������Ĳ���λ��
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();
	}

	//�Ƿ���Ҫ��������
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/***
	 * �Ƿ���Ҫ�ƶ����� inner.getMeasuredHeight():��ȡ���ǿؼ����ܸ߶�
	 * 
	 * getHeight()����ȡ������Ļ�ĸ߶�
	 * 
	 * @return
	 */
	public boolean isNeedMove() {
		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		//0�Ƕ���,�����Ǹ��ǵײ�
		if (scrollY == 0 || scrollY == offset) {
			return true;
		}
		return false;
	}

}