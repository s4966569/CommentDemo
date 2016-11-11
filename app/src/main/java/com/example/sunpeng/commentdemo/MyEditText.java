package com.example.sunpeng.commentdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by sunpeng on 2016/11/11.
 */

public class MyEditText extends EditText {
    private OnKeyboardHiddenListener onKeyboardHiddenListener;
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
            if(onKeyboardHiddenListener!=null){
                onKeyboardHiddenListener.onKeyHidden();
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public OnKeyboardHiddenListener getOnKeyboardHiddenListener() {
        return onKeyboardHiddenListener;
    }

    public void setOnKeyboardHiddenListener(OnKeyboardHiddenListener onKeyboardHiddenListener) {
        this.onKeyboardHiddenListener = onKeyboardHiddenListener;
    }

    public interface OnKeyboardHiddenListener{
        void onKeyHidden();
    }
}
