package com.ice.wb.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ice.wb.R;


/**
 * 4位自定义密码输入框View
 * Created by lvzhengbin on 2019/11/5.
 */
public class LzPasswordInputView extends RelativeLayout{

    private Context mContext;
    private TextView[] mPasswordTvList;
    private ImageView[] mPasswordImgList;
    private EditText mEditText;

    private OnPasswordInputFinishListener mOnPasswordInputFinishListener;

    public LzPasswordInputView(Context context) {
        super(context);
        init(context);
    }

    public LzPasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LzPasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        View view = View.inflate(context, R.layout.wb_widget_pay_password_layout, this);
        initView(view);
        addListener();
        //addView(view);
    }

    private void initView(View view) {
        mEditText = (EditText) view.findViewById(R.id.et_pwd_input);

        mPasswordTvList = new TextView[4];
        mPasswordImgList = new ImageView[4];

        mPasswordTvList[0] = (TextView) view.findViewById(R.id.ktv_tv_pass1);
        mPasswordTvList[1] = (TextView) view.findViewById(R.id.ktv_tv_pass2);
        mPasswordTvList[2] = (TextView) view.findViewById(R.id.ktv_tv_pass3);
        mPasswordTvList[3] = (TextView) view.findViewById(R.id.ktv_tv_pass4);

        mPasswordImgList[0] = (ImageView) view.findViewById(R.id.ktv_img_pass1);
        mPasswordImgList[1] = (ImageView) view.findViewById(R.id.ktv_img_pass2);
        mPasswordImgList[2] = (ImageView) view.findViewById(R.id.ktv_img_pass3);
        mPasswordImgList[3] = (ImageView) view.findViewById(R.id.ktv_img_pass4);
    }

    private void addListener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("LIZHI_LV", "VALUE = " + s.toString());

                int length = s.length();
                if (length == 0){
                    clearInputPassword();
                    return;
                }
                for (int i = 0; i < 4; i++){
                    setInputPassword(i, i < length ? s.subSequence(i, i+1).toString() : "");
                }
            }
        });

        mPasswordTvList[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    String strPassword = "";
                    for (int i = 0; i < 4; i++) {
                        strPassword += mPasswordTvList[i].getText().toString().trim();
                    }
                    if (mOnPasswordInputFinishListener != null){
                        mOnPasswordInputFinishListener.onInputFinish(strPassword);
                    }
                }
            }
        });
    }

    /**
     * 设置输入密码
     * @param inputIndex 当前输入密码格位置索引
     * @param pswText 密码值
     */
    public void setInputPassword(int inputIndex, String pswText){
        Log.i("LIZHI_LV", "setInputPassword inputIndex = " + inputIndex + ", pswText = " + pswText);
        if (mPasswordTvList != null && mPasswordTvList.length > inputIndex && mPasswordImgList != null && mPasswordImgList.length > inputIndex){
            mPasswordImgList[inputIndex].setVisibility(TextUtils.isEmpty(pswText) ? INVISIBLE : VISIBLE);
            mPasswordTvList[inputIndex].setVisibility(TextUtils.isEmpty(pswText) ? VISIBLE : INVISIBLE);
            mPasswordTvList[inputIndex].setText(pswText);
        }
    }

    /**
     * 清除所输入的密码
     */
    public void clearInputPassword(){
        for (int i = 3; i >= 0; i--){
            mPasswordTvList[i].setText("");
            mPasswordTvList[i].setVisibility(VISIBLE);
            mPasswordImgList[i].setVisibility(INVISIBLE);
        }
    }

    public void setOnPasswordInputFinishListener(OnPasswordInputFinishListener onPasswordInputFinishListener) {
        this.mOnPasswordInputFinishListener = onPasswordInputFinishListener;
    }

    /**
     * 密码输入完成 事件回调接口
     */
    public interface OnPasswordInputFinishListener {
        void onInputFinish(String password);
    }
}
