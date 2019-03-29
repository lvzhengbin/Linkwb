package com.ice.wb.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ice.wb.R;

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2019/2/23
 */
public class TabButtonView extends RelativeLayout {

    private CircleWaveView mCircleWaveView;
    private TextView mTextView;
    private ImageView mImage;

    private Drawable mDrawable;
    private int mTextSize ;
    private int mNormalColor;
    private int mSelectedColor;
    private int mPadding;
    private int mTabIndex;
    private int mBorderMargin;
    private int mMidMargin;
    private String mPrimaryText;

    private static int DEFAULT_PADDING = 8;
    private static String DEFAULT_TEXT_NORMAL_COLOR = "#808080";
    private static String DEFAULT_TEXT_SELECTED_COLOR = "#FF3B47";

    public TabButtonView(Context context) {
        super(context);
        init(context, null);
    }

    public TabButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TabButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.wb_tab_button_layout, this);
        mTextView = (TextView) inflate.findViewById(R.id.tv_tab_name);
        mImage = (ImageView) inflate.findViewById(R.id.iv_tab_icon);
        mCircleWaveView = (CircleWaveView) inflate.findViewById(R.id.circle_wave_view);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Wb_TabButton);
        //setGravity(Gravity.CENTER);
        //setOrientation(LinearLayout.VERTICAL);
        String textColorStr = null;
        String selectedColorStr = null;
        mBorderMargin = (int) getResources().getDimension(R.dimen.wb_main_tab_border_margin);
        mMidMargin = (int) getResources().getDimension(R.dimen.wb_main_tab_mid_margin);
        mTextSize = getResources().getDimensionPixelSize(R.dimen.wb_textsize_10);
        if (typedArray != null) {
            try {
                mDrawable = typedArray.getDrawable(R.styleable.Wb_TabButton_wb_tab_icon);
                textColorStr = typedArray.getString(R.styleable.Wb_TabButton_wb_tab_text_normal_color);
                selectedColorStr = typedArray.getString(R.styleable.Wb_TabButton_wb_tab_text_selected_color);
                mTabIndex = typedArray.getInt(R.styleable.Wb_TabButton_wb_tab_index, 0);
                mPadding = typedArray.getDimensionPixelSize(R.styleable.Wb_TabButton_wb_tab_icon_padding, DEFAULT_PADDING);
                mPrimaryText = typedArray.getString(R.styleable.Wb_TabButton_wb_tab_button_text);
                mTextSize = typedArray.getDimensionPixelSize(R.styleable.Wb_TabButton_wb_tab_button_text_size, mTextSize);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                typedArray.recycle();
            }
        }
        if(mPadding == 0) {
            mPadding = DEFAULT_PADDING;
        }
        if (TextUtils.isEmpty(mPrimaryText)) {
            mPrimaryText = "...";
        }
        if(mDrawable != null) {
            mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth() , mDrawable.getIntrinsicHeight());
        }
        mNormalColor = Color.parseColor(TextUtils.isEmpty(textColorStr) ? DEFAULT_TEXT_NORMAL_COLOR : textColorStr);
        mSelectedColor = Color.parseColor(TextUtils.isEmpty(selectedColorStr) ? DEFAULT_TEXT_SELECTED_COLOR : selectedColorStr);
        initTabImage();
        initTextView();
    }

    private void initTabImage() {
        if (mDrawable != null){
            /*mImage = new ImageView(getContext());
            mImage.setImageDrawable(mDrawable);
            addView(mImage);*/

            mImage.setImageDrawable(mDrawable);
        }
    }

    private void initTextView() {
        //mTextView = new TextView(getContext());
        mTextView.setText(mPrimaryText);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTextView.setTextColor(mNormalColor);
        mTextView.setGravity(Gravity.CENTER);
        //MarginLayoutParams params = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //adjustLayout(params);
        //params.topMargin = mPadding;
        //mTextView.setLayoutParams(params);
        //addView(mTextView);
    }

    private void adjustLayout(LayoutParams lp){
        if(mTabIndex == 0){
            lp.rightMargin = mBorderMargin;
        }else if(mTabIndex == 1){
            lp.rightMargin = mMidMargin;
        }else if(mTabIndex == 3){
            lp.leftMargin = mMidMargin;
        }else if(mTabIndex == 4){
            lp.leftMargin = mBorderMargin;
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if(mImage != null) {
            mImage.setSelected(selected);
        }
        if(mTextView != null ) {
            if(selected) {
                mTextView.setTextColor(mSelectedColor);
            } else {
                mTextView.setTextColor(mNormalColor);
            }
        }
        if (mCircleWaveView != null && selected){
            mCircleWaveView.startAnimator();
        }
    }
}
