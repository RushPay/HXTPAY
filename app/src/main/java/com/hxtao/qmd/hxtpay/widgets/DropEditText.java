package com.hxtao.qmd.hxtpay.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.hxtao.qmd.hxtpay.R;

/**
 * @Description:
 * @Author: Cyf on 2017/3/6.
 */

public class DropEditText extends FrameLayout implements View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText mEditText;  // 输入框
    private ImageView mDropImage; // 右边的图片按钮
    private PopupWindow mPopup; // 点击图片弹出popupwindow
    private UserListView mPopView; // popupwindow的布局

    private int mDrawableLeft;
    private int mDropMode; // flow_parent or wrap_content
    private String mHit;
    private int position;

    private ChooseText chooseText;

    public DropEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.edit_layout, this);
        mPopView = (UserListView) LayoutInflater.from(context).inflate(R.layout.pop_view, null);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DropEditText, defStyle, 0);
        mDrawableLeft = ta.getResourceId(R.styleable.DropEditText_drawableRight, R.drawable.arrow_down);
        mDropMode = ta.getInt(R.styleable.DropEditText_dropMode, 0);
        mHit = ta.getString(R.styleable.DropEditText_hint);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mEditText = (EditText) findViewById(R.id.dropview_edit);
        mDropImage = (ImageView) findViewById(R.id.dropview_image);

        mEditText.setSelectAllOnFocus(true);
        mDropImage.setImageResource(mDrawableLeft);

        if(!TextUtils.isEmpty(mHit)) {
            mEditText.setHint(mHit);
        }

        mDropImage.setOnClickListener(this);
        mPopView.setOnItemClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 如果布局发生改
        // 并且dropMode是flower_parent
        // 则设置ListView的宽度
        if(changed && 0 == mDropMode) {
            mPopView.setListWidth(getMeasuredWidth());
        }
    }

    /**
     * 设置Adapter
     * @param adapter ListView的Adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        mPopView.setAdapter(adapter);
        mPopup = new PopupWindow(mPopView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopup.setBackgroundDrawable(getResources().getDrawable(R.color.c_333333));
        mPopup.setFocusable(true); // 让popwin获取焦点
    }

    /**
     * 获取输入框内的内容
     * @return String content
     */
    public String getText() {
        return mEditText.getText().toString();
    }

    public void addTextChangedListener(){

    }
    /**
     * 添加输入框内的内容
     */
    public void setText(String s) {
         mEditText.setText(s);
    }

    public void setError(String s){
        mEditText.setError(s);
    }

    public void setInputType() {
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
    }
    public void setHint(String s) {
        mEditText.setHint(s);
    }


    @Override
    public void onClick(View v) {
        if(mPopup!=null&&v.getId() == R.id.dropview_image) {
            if(mPopup.isShowing()) {
                mPopup.dismiss();
                return;
            }
            mPopup.showAsDropDown(this, 0, 5);
        }
    }
    public void getPosition(ChooseText chooseText){
        this.chooseText=chooseText;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        mEditText.setText(mPopView.getAdapter().getItem(position).toString());
        chooseText.getChoosePostion(position);
        mPopup.dismiss();
    }

}
