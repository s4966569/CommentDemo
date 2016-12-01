package com.example.sunpeng.commentdemo;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by sunpeng on 2016/11/29.
 */

public class CommentDialogFragment extends DialogFragment{
    private Context mContext;
    private View view,view_bg;
    private TextView tv_send_enable,tv_send_disable,tv_words_count;
    private EditText et_comment;
    private int mClickPosition , mLastClickPosition;
    private int commentType;
    InputMethodManager imm;
    private boolean isKeyboardShown = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        view = inflater.inflate(R.layout.pop_send_comment,container);
        et_comment = (EditText) view.findViewById(R.id.et_comment);
        et_comment.setTextIsSelectable(true);
        tv_words_count = (TextView) view.findViewById(R.id.tv_words_count);
        view_bg = view.findViewById(R.id.view_bg);
        view_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dismiss();
            }
        });
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        et_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isKeyboardShown)
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(bottom < oldBottom){
//                    view_bg.setFocusable(true);
//                    view_bg.setFocusableInTouchMode(true);
//                    view_bg.requestFocus();
                    isKeyboardShown = true;
                    Log.i("keyBoard",isKeyboardShown+"");
                }else {
                    isKeyboardShown = false;
                    Log.i("keyBoard",isKeyboardShown+"");
                }
            }
        });
        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLastClickPosition = mClickPosition;
    }


    @Override
    public void onResume() {
        super.onResume();
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
