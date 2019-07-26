package com.saber.virtualposition.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView中item间隔
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

/*        //在该类的getItemOffsets方法中设置item各个方向的间距。这里面加入了如果是RecyclerView的第一个子项，则设置该子项上方的间距。
        //运行出来之后就会发现每个item四周都有间距了
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace;
        }*/

    }

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }
}