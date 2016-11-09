package com.example.sunpeng.commentdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sunpeng on 16-11-7.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommentInfo> commentInfos;
    private LayoutInflater mLayoutInflater;
    public MyAdapter(Context context,List<CommentInfo> commentInfos) {
        mContext=context;
        this.commentInfos = commentInfos;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == CommentInfo.LEVEL_0)
            return new CommentHolderView(mLayoutInflater.inflate(R.layout.comment_item_level_1,parent,false));
        else if(viewType==CommentInfo.LEVEL_1)
            return new CommentHolderViewEx(mLayoutInflater.inflate(R.layout.comment_item_level_2,parent,false));
        else if(viewType == CommentInfo.LEVEL_2)
            return new LoadMoreHolderView(mLayoutInflater.inflate(R.layout.comment_item_more,parent,false));
        else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof CommentHolderView){
            ((CommentHolderView)holder).tv_name.setText(commentInfos.get(position).getName());
            ((CommentHolderView)holder).tv_comment.setText(commentInfos.get(position).getComment());
            if(!TextUtils.isEmpty(commentInfos.get(position).getTime()))
                ((CommentHolderView)holder).tv_time.setText(commentInfos.get(position).getTime());
        }else if(holder instanceof CommentHolderViewEx){
            ((CommentHolderViewEx)holder).tv_name.setText(commentInfos.get(position).getName());
            ((CommentHolderViewEx)holder).tv_comment.setText(commentInfos.get(position).getComment());
            if(!TextUtils.isEmpty(commentInfos.get(position).getTime()))
                ((CommentHolderViewEx)holder).tv_time.setText(commentInfos.get(position).getTime());
        }else if(holder instanceof LoadMoreHolderView){
            ((LoadMoreHolderView) holder).tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommentInfo commentInfo = new CommentInfo();
                    commentInfo.setName("小红"+position);
                    commentInfo.setComment(commentInfos.get(position).getComment()+position);
                    commentInfo.setLevel(CommentInfo.LEVEL_1);
                    commentInfo.setTime(Calendar.getInstance().getTime().toString());
                    addItem(commentInfo,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return commentInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(commentInfos.get(position).getLevel()==CommentInfo.LEVEL_0)
            return CommentInfo.LEVEL_0;
        else if(commentInfos.get(position).getLevel()==CommentInfo.LEVEL_1)
            return CommentInfo.LEVEL_1;
        else if(commentInfos.get(position).getLevel()==CommentInfo.LEVEL_2)
            return CommentInfo.LEVEL_2;
        else
            return 0;
    }

    public void addItem(CommentInfo commentInfo,int position){
        commentInfos.add(position,commentInfo);
//        notifyItemInserted(position);
//        notifyItemRangeChanged(position,commentInfos.size());
        notifyDataSetChanged();
    }
    class CommentHolderView extends RecyclerView.ViewHolder{
        TextView tv_name,tv_comment,tv_thumbsUp,tv_time;
        ImageView iv_portrait;
        public CommentHolderView(View itemView) {
            super(itemView);
            iv_portrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_thumbsUp= (TextView) itemView.findViewById(R.id.tv_thumbsUp);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    class CommentHolderViewEx extends RecyclerView.ViewHolder{
        TextView tv_name,tv_comment,tv_thumbsUp,tv_time;
        public CommentHolderViewEx(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_thumbsUp= (TextView) itemView.findViewById(R.id.tv_thumbsUp);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    class LoadMoreHolderView extends RecyclerView.ViewHolder{

        TextView tv_more;
        public LoadMoreHolderView(View itemView) {
            super(itemView);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
        }
    }
}
