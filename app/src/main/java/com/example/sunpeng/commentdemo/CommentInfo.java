package com.example.sunpeng.commentdemo;

/**
 * Created by sunpeng on 16-11-7.
 */

public class CommentInfo {
    public static final int LEVEL_0 = 0x01;
    public static final int LEVEL_1 = 0x02;
    public static final int LEVEL_2 = 0x03;
    private String name="林丽";
    private String comment="很好的主题大家都来谈一谈啊";
    private String time;
    private int level = LEVEL_0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
