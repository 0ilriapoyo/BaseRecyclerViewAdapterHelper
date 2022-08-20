package com.chad.baserecyclerviewadapterhelper.activity;


import android.animation.ValueAnimator;
import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.adapter.DragAndSwipeAdapter;
import com.chad.baserecyclerviewadapterhelper.adapter.HomeTopHeaderAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.utils.Tips;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.QuickAdapterHelper;
import com.chad.library.adapter.base.dragswipe.DefaultDragAndSwipe;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认实现拖动与侧滑效果
 * Drag and Drag effects are implemented by default
 */
public class DefaultDragAndSwipeActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private DragAndSwipeAdapter mAdapter;
    private QuickAdapterHelper helper;
    DefaultDragAndSwipe defaultDragAndSwipe = new DefaultDragAndSwipe.Builder()
            .setDragMoveFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN)
            .setSwipeMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_recycler);

        setBackBtn();
        setTitle("Default Drag And Swipe");

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 拖拽监听
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);  //震动70毫秒
                vib.vibrate(70);
                Log.d(TAG, "drag start");
                final QuickViewHolder holder = ((QuickViewHolder) viewHolder);
                // 开始时，item背景色变化，demo这里使用了一个动画渐变，使得自然
                int startColor = Color.WHITE;
                int endColor = Color.rgb(245, 245, 245);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                    v.addUpdateListener(animation -> holder.itemView.setBackgroundColor((int) animation.getAnimatedValue()));
                    v.setDuration(300);
                    v.start();
                }
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "drag end");
                final QuickViewHolder holder = ((QuickViewHolder) viewHolder);
                // 结束时，item背景色变化，demo这里使用了一个动画渐变，使得自然
                int startColor = Color.rgb(245, 245, 245);
                int endColor = Color.WHITE;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                    v.addUpdateListener(animation -> holder.itemView.setBackgroundColor((int) animation.getAnimatedValue()));
                    v.setDuration(300);
                    v.start();
                }
            }
        };

        OnItemSwipeListener swipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e("yyyyy", "onItemSwipeStart");
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e("yyyyy", "clearView");
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e("yyyyy", "onItemSwiped");
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                Log.e("yyyyy", "onItemSwipeMoving");
            }
        };

        List<String> mData = generateData(50);
        mAdapter = new DragAndSwipeAdapter();
        helper = new QuickAdapterHelper.Builder(mAdapter)
                .build();
        mRecyclerView.setAdapter(helper.getAdapter());
        mAdapter.submitList(mData);
        defaultDragAndSwipe.attachToRecyclerView(mRecyclerView)
                .setAdapterImpl(mAdapter)
                .setItemDragListener(listener)
                .setItemSwipeListener(swipeListener);
        mAdapter.setOnItemClickListener((BaseQuickAdapter.OnItemClickListener) (adapter, view, position) -> Tips.show("点击了：" + position));
    }

    private List<String> generateData(int size) {
        ArrayList<String> data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add("item " + i);
        }
        return data;
    }
}