package com.example.sunpeng.commentdemo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private View root;
    private LinearLayout ll_edit;
    private XSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private EditText et;
    private Button btn_add;
    private MyAdapter adapter;
    private List<CommentInfo> commentInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.activity_main);
        ll_edit = (LinearLayout) findViewById(R.id.ll_edit);
        swipeRefreshLayout = (XSwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_add = (Button) findViewById(R.id.btn_add);
        et = (EditText) findViewById(R.id.et);
        initData();
        adapter = new MyAdapter(this, commentInfoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setEnabled(false);

        ll_edit.getRootView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if(bottom<oldBottom){
//                    Log.i("key","keyboard show");
//                }else {
//                    Log.i("key","keyboard hidden");
//                }
//                Log.i("bottom::", "old:" + oldTop + "_____new:" + top);
                Log.i("y",""+ll_edit.getTranslationY());
            }
        });

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
                    if (!TextUtils.isEmpty(et.getText())){
                        et.setText("");
                    }
                }
                return true;
            }
        });

        swipeRefreshLayout.setmOnTouchCallBack(new XSwipeRefreshLayout.OnTouchCallBack() {
            @Override
            public boolean onTouch(MotionEvent event) {
                if(et.hasFocus()){
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
}
