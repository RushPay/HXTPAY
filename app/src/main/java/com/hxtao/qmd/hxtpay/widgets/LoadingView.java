package com.hxtao.qmd.hxtpay.widgets;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.utils.Uiutils;


/**
 * 描 述: 加载中动效 创 建： 2016/7/20 版 本：
 */
public class LoadingView extends FrameLayout {

	private ImageView img_loading;
	private ImageView img_yingzi;
	private AnimatorSet set;
	public int duration = 900;

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
		// initAnim(duration);
	}

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_loading2, this);

		// img_loading = (ImageView) view.findViewById(R.id.img_loading);
		// img_yingzi = (ImageView) view.findViewById(R.id.img_yingzi);

	}

	private void initAnim(int duration) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(img_loading, "translationY", 0,
				Uiutils.dip2px(getContext(), 15), 0);

		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.setRepeatMode(Animation.RESTART);
		animator.setRepeatCount(1000);
		animator.setDuration(duration);

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(img_yingzi, "scaleX", 1, 1.5f, 1);
		animator2.setInterpolator(new AccelerateDecelerateInterpolator());
		animator2.setRepeatMode(Animation.RESTART);
		animator2.setRepeatCount(1000);
		animator2.setDuration(duration);

		ObjectAnimator animator3 = ObjectAnimator.ofFloat(img_yingzi, "scaleY", 1, 1.5f, 1);
		animator3.setInterpolator(new AccelerateDecelerateInterpolator());
		animator3.setRepeatMode(Animation.RESTART);
		animator3.setRepeatCount(1000);
		animator3.setDuration(duration);

		set = new AnimatorSet();
		set.play(animator).with(animator2).with(animator3);
		set.start();
	}

	private void start() {
		if (!set.isRunning())
			set.start();
	}

	private void stop() {
		if (set != null && set.isRunning())
			set.cancel();
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		// if (visibility == View.VISIBLE)
		// start();
		// else
		// stop();

	}
}
