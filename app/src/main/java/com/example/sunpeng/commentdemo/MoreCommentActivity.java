package com.example.sunpeng.commentdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

/**
 * Created by sunpeng on 2016/11/15.
 */

public class MoreCommentActivity extends Activity {
    private View root,view_bg;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private EditText et_comment;
    private Button btn_add,btn_send;
    private MoreCommentAdapter adapter;
    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private int mPosition;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_commetn);
        root = findViewById(R.id.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_add = (Button) findViewById(R.id.btn_add);
        initData();
        adapter = new MoreCommentAdapter(this, commentInfoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setEnabled(false);

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
}
