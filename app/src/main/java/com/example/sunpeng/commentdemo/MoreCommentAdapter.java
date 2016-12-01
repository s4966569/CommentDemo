package com.example.sunpeng.commentdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sunpeng on 2016/11/15.
 */

public class MoreCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MAIN_COMMENT=0x00;
    public static final int CHILD_COMMENT = 0x01;

    private List<CommentInfo> commentInfos;
    private Context mContext;
    private LayoutInflater inflater;
    public MoreCommentAdapter(Context context, List<CommentInfo> commentInfos) {
        this.mContext = context;
        this.commentInfos = commentInfos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == MAIN_COMMENT)
            return new HeaderCommentViewHolder(inflater.inflate(R.layout.item_more_comment_level_1,parent,false));
        else return new ChildCommentViewHolder(inflater.inflate(R.layout.item_comment_level_0,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MoreCommentAdapter.HeaderCommentViewHolder){
            ((MoreCommentAdapter.HeaderCommentViewHolder)holder).tv_name.setText(commentInfos.get(position).getName());
            ((MoreCommentAdapter.HeaderCommentViewHolder)holder).tv_comment.setText(commentInfos.get(position).getComment());
            if(!TextUtils.isEmpty(commentInfos.get(position).getTime()))
                ((MoreCommentAdapter.HeaderCommentViewHolder)holder).tv_time.setText(commentInfos.get(position).getTime());
        }else if(holder instanceof MoreCommentAdapter.ChildCommentViewHolder){
            ((MoreCommentAdapter.ChildCommentViewHolder)holder).tv_name.setText(commentInfos.get(position).getName());
            ((MoreCommentAdapter.ChildCommentViewHolder)holder).tv_comment.setText(commentInfos.get(position).getComment());
//            ((MoreCommentAdapter.ChildCommentViewHolder)holder).tv_comment.setTypeface(null,Typeface.NORMAL);
            if(!TextUtils.isEmpty(commentInfos.get(position).getTime()))
                ((MoreCommentAdapter.ChildCommentViewHolder)holder).tv_time.setText(commentInfos.get(position).getTime());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return MAIN_COMMENT;
        else
            return CHILD_COMMENT;
    }

    @Override
    public int getItemCount() {
        return commentInfos.size();
    }

    public void addItem(CommentInfo commentInfo,int position){
        commentInfos.add(position,commentInfo);
        notifyDataSetChanged();
    }
    class HeaderCommentViewHolder  extends RecyclerView.ViewHolder{
        TextView tv_name,tv_comment,tv_thumbsUp,tv_time,tv_comment_count;
        View ll_main_comment;
        ImageView iv_portrait;

        public HeaderCommentViewHolder(final View itemView) {
            super(itemView);
            iv_portrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_thumbsUp= (TextView) itemView.findViewById(R.id.tv_thumbs_count);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            ll_main_comment = itemView.findViewById(R.id.ll_main_comment);
        }
    }

    class ChildCommentViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_comment,tv_thumbsUp,tv_time;
        ImageView iv_portrait;
        public ChildCommentViewHolder(View itemView) {
            super(itemView);
            iv_portrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_thumbsUp= (TextView) itemView.findViewById(R.id.tv_thumbs_count);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}