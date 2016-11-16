package com.example.sunpeng.commentdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {

    private View root,view_bg;
    private LinearLayout ll_edit;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private EditText et,et_comment;
    private Button btn_add,btn_send;
    private CommentAdapter adapter;
    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private int mPosition;
    private PopupWindow mPopupWindow;
    private boolean mIsKeyboardShown = false;
    private boolean isLevel0 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.activity_main);
        ll_edit = (LinearLayout) findViewById(R.id.ll_edit);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_add = (Button) findViewById(R.id.btn_add);
        initData();
        adapter = new CommentAdapter(this, commentInfoList);
        adapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onClick(CommentAdapter adapter, View item, int position) {
                mPosition = position;
//                et.requestFocus();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                showPopupWindow();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setEnabled(false);

        root.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.i("height","old::"+oldBottom+"____new::"+bottom);
                if(bottom < oldBottom){
//                    mIsKeyboardShown = true;
//                    if(!et.hasFocus())
//                        et.requestFocus();
                }else {
                    mIsKeyboardShown = false;
//                    swipeRefreshLayout.requestFocus();
                }

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition=0;
                isLevel0 = true;
                showPopupWindow();
            }
        });

//        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    if (!TextUtils.isEmpty(et.getText())) {
//                        CommentInfo commentInfo = new CommentInfo();
//                        commentInfo.setName("小红" + mPosition);
//                        commentInfo.setComment(et.getText().toString());
//                        commentInfo.setLevel(CommentInfo.LEVEL_1);
//                        commentInfo.setTime(Calendar.getInstance().getTime().toString());
//                        adapter.addItem(commentInfo, mPosition + 1);
//                        et.setText("");
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
//                        swipeRefreshLayout.requestFocus();
//                    }
//                }
//                return true;
//            }
//        });

//        swipeRefreshLayout.setmOnTouchCallBack(new XSwipeRefreshLayout.OnTouchCallBack() {
//            @Override
//            public boolean onTouch(MotionEvent event) {
//                if (et.hasFocus()) {
//                    swipeRefreshLayout.requestFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            CommentInfo info = new CommentInfo();
            if (i % 3 == 1) {
                info.setName("张霖");
                info.setLevel(CommentInfo.LEVEL_1);
            } else if (i % 3 == 2) {
                info.setLevel(CommentInfo.LEVEL_2);
            }
            commentInfoList.add(info);
        }
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) ||
                (codePoint == 0xA) || (codePoint == 0xD) ||
                ((codePoint >= 0x20) && codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    private boolean isKeyboardShown(View rootView){
        final int diffHeight = 100;
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        DisplayMetrics metrics = rootView.getResources().getDisplayMetrics();
        int height = rootView.getBottom() - rect.bottom;
        Log.i("height","root::"+rootView.getBottom()+"____visible::"+rect.bottom);
        return  height > diffHeight * metrics.density;
    }

    private void showPopupWindow(){
        if(mPopupWindow == null){
            final View pop = getLayoutInflater().inflate(R.layout.pop_send_comment,null);
            et_comment = (EditText) pop.findViewById(R.id.et_comment);
            btn_send = (Button) pop.findViewById(R.id.btn_send);
            view_bg = pop.findViewById(R.id.view_bg);
            view_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_comment.getWindowToken(), 0);
                    mPopupWindow.dismiss();
                }
            });
            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(et_comment.getText())) {
                        CommentInfo commentInfo = new CommentInfo();
                        commentInfo.setName("小红" + mPosition);
                        commentInfo.setComment(et_comment.getText().toString());
                        commentInfo.setTime(Calendar.getInstance().getTime().toString());
                        if(mPosition==0 && isLevel0){
                            commentInfo.setLevel(CommentInfo.LEVEL_0);
                            adapter.addItem(commentInfo, mPosition);
                            isLevel0=false;
                        }else {
                            commentInfo.setLevel(CommentInfo.LEVEL_1);
                            adapter.addItem(commentInfo, mPosition + 1);
                        }
                        et_comment.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(et_comment.getWindowToken(), 0);
                        mPopupWindow.dismiss();
                    }
                }
            });
            mPopupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//            mPopupWindow.setAnimationStyle(R);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        }
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM,0,0);
        et_comment.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
