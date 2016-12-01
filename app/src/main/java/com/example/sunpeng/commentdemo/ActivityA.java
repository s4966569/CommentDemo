package com.example.sunpeng.commentdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.visible;

/**
 * Created by sunpeng on 2016/11/29.
 */

public class ActivityA extends Activity {

    private Context mContext;
    private View view,view_bg,root_view;
    private TextView tv_send_enable,tv_send_disable,tv_words_count;
    private EditText et_comment;
    private int mClickPosition , mLastClickPosition;
    private int commentType;
    private  boolean isVisible;
    InputMethodManager imm;
    private boolean isKeyboardShown = false;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_send_comment);
        mContext = this;
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        root_view = findViewById(R.id.root_view);
        et_comment = (EditText) findViewById(R.id.et_comment);
        et_comment.setTextIsSelectable(true);
        tv_words_count = (TextView) findViewById(R.id.tv_words_count);
        view_bg = findViewById(R.id.view_bg);
        view_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(et_comment.getWindowToken(), 0);
                finish();
            }
        });

        et_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isKeyboardShown)
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
//        et_comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus)
//                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                else imm.hideSoftInputFromWindow(et_comment.getWindowToken(), 0);
//            }
//        });
        root_view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(bottom < oldBottom){
                    //键盘弹起状态
                    isKeyboardShown = true;
                    Log.i("keyBoard",isKeyboardShown+"");
                }else{
                    //键盘收起状态
                    isKeyboardShown = false;
                    Log.i("keyBoard",isKeyboardShown+"");
//                    if(!isFirst){
//                        root_view.setFocusable(true);
//                        root_view.setFocusableInTouchMode(true);
//                        root_view.requestFocus();
//                    }else {
//                        isFirst = false;
//                    }
                }
            }
        });
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isKeyboardShown)
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
