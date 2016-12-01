package com.example.sunpeng.commentdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sunpeng on 16-11-7.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommentInfo> commentInfos;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener onItemClickListener;
    public CommentAdapter(Context context, List<CommentInfo> commentInfos) {
        mContext=context;
        this.commentInfos = commentInfos;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == CommentInfo.LEVEL_0)
            return new CommentHolderView(mLayoutInflater.inflate(R.layout.item_comment_level_0,parent,false));
        else if(viewType==CommentInfo.LEVEL_1)
            return new CommentHolderViewEx(mLayoutInflater.inflate(R.layout.item_comment_level_1,parent,false));
        else if(viewType == CommentInfo.LEVEL_2)
            return new LoadMoreHolderView(mLayoutInflater.inflate(R.layout.item_comment_more,parent,false));
        else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof CommentHolderView){
            ((CommentHolderView)holder).tv_name.setText(commentInfos.get(position).getName());
            ((CommentHolderView)holder).tv_comment.setText(commentInfos.get(position).getComment());
//            ((CommentHolderView)holder).tv_comment.getPaint().setFakeBoldText(true);
            if(!TextUtils.isEmpty(commentInfos.get(position).getTime()))
                ((CommentHolderView)holder).tv_time.setText(commentInfos.get(position).getTime());
        }else if(holder instanceof CommentHolderViewEx){
            ((CommentHolderViewEx)holder).tv_name.setText(commentInfos.get(position).getName());
            ((CommentHolderViewEx)holder).tv_comment.setText(commentInfos.get(position).getComment());
            if(!TextUtils.isEmpty(commentInfos.get(position).getTime()))
                ((CommentHolderViewEx)holder).tv_time.setText(commentInfos.get(position).getTime());
        }else if(holder instanceof LoadMoreHolderView){

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
        public CommentHolderView(final View itemView) {
            super(itemView);
            iv_portrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_thumbsUp= (TextView) itemView.findViewById(R.id.tv_thumbs_count);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            if(onItemClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(CommentAdapter.this,itemView,getLayoutPosition());
                    }
                });
            }
        }
    }

    class CommentHolderViewEx extends RecyclerView.ViewHolder{
        TextView tv_name,tv_comment,tv_thumbsUp,tv_time;
        public CommentHolderViewEx(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_thumbsUp= (TextView) itemView.findViewById(R.id.tv_thumbs_count);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    class LoadMoreHolderView extends RecyclerView.ViewHolder{

        TextView tv_more;
        public LoadMoreHolderView(View itemView) {
            super(itemView);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    CommentInfo commentInfo = new CommentInfo();
//                    commentInfo.setName("小红"+getLayoutPosition());
//                    commentInfo.setComment(commentInfos.get(getLayoutPosition()).getComment()+getLayoutPosition());
//                    commentInfo.setLevel(CommentInfo.LEVEL_1);
//                    commentInfo.setTime(Calendar.getInstance().getTime().toString());
//                    addItem(commentInfo,getLayoutPosition());
                    Intent intent = new Intent(mContext,MoreCommentActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(CommentAdapter adapter, View item, int position);
    }
}
