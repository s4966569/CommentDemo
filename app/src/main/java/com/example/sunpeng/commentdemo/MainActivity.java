package com.example.sunpeng.commentdemo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {

    private View root;
    private LinearLayout ll_edit;
    private XSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MyEditText et;
    private Button btn_add;
    private MyAdapter adapter;
    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.activity_main);
        ll_edit = (LinearLayout) findViewById(R.id.ll_edit);
        swipeRefreshLayout = (XSwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_add = (Button) findViewById(R.id.btn_add);
        et = (MyEditText) findViewById(R.id.et);
        initData();
        adapter = new MyAdapter(this, commentInfoList);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(MyAdapter adapter, View item, int position) {
                mPosition = position;
                et.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setEnabled(false);

        root.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.i("height","old::"+oldBottom+"____new::"+bottom);
                isKeyboardShown(root);
            }
        });
//        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                boolean  b = isKeyboardShown(root);
//                Log.i("keyboard",b+"");
//            }
//        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentInfo info = new CommentInfo();
                info.setName("小强");
                info.setLevel(CommentInfo.LEVEL_0);
                commentInfoList.add(0, info);
                adapter.notifyItemInserted(0);
            }
        });

        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (!TextUtils.isEmpty(et.getText())) {
                        CommentInfo commentInfo = new CommentInfo();
                        commentInfo.setName("小红" + mPosition);
                        commentInfo.setComment(et.getText().toString());
                        commentInfo.setLevel(CommentInfo.LEVEL_1);
                        commentInfo.setTime(Calendar.getInstance().getTime().toString());
                        adapter.addItem(commentInfo, mPosition + 1);
                        et.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                        swipeRefreshLayout.requestFocus();
                    }
                }
                return true;
            }
        });

        et.setOnKeyboardHiddenListener(new MyEditText.OnKeyboardHiddenListener() {
            @Override
            public void onKeyHidden() {
                swipeRefreshLayout.requestFocus();
                Log.i("et","keyboardHidden!");
            }
        });

        swipeRefreshLayout.setmOnTouchCallBack(new XSwipeRefreshLayout.OnTouchCallBack() {
            @Override
            public boolean onTouch(MotionEvent event) {
                if (et.hasFocus()) {
                    swipeRefreshLayout.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("conf", "change");
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
}
