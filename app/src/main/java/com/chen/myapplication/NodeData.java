package com.chen.myapplication;

import android.graphics.RectF;

public class NodeData {
    private int progressNode;
    private RectF rectF;

    public NodeData(int progressNode, RectF rectF) {
        this.progressNode = progressNode;
        this.rectF = rectF;
    }

    public int getProgressNode() {
        return progressNode;
    }

    public void setProgressNode(int progressNode) {
        this.progressNode = progressNode;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }
}
