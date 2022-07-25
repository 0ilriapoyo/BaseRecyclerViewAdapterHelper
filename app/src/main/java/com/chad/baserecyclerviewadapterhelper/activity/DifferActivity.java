package com.chad.baserecyclerviewadapterhelper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.adapter.diffUtil.DiffUtilAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.DiffUtilDemoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limuyang
 * Date: 2019/7/14O
 */
public class DifferActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Button itemChangeBtn;
    private Button removeBtn;
    private Button addBtn;

    private DiffUtilAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffutil);
        setBackBtn();
        setTitle("DiffUtil Use");


        findView();
        initRv();
        initClick();

    }

    @Override
    protected void onStart() {
        super.onStart();

        // 增加延迟，模拟网络加载
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.submitList(DataServer.getDiffUtilDemoEntities());
            }
        }, 1500);
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.diff_rv);
        itemChangeBtn = findViewById(R.id.item_change_btn);
        removeBtn = findViewById(R.id.btn_remove);
        addBtn = findViewById(R.id.btn_add);
    }

    private void initRv() {
        mAdapter = new DiffUtilAdapter();
        // 打开空布局功能
        mAdapter.setEmptyViewEnable(true);
        // 传入 空布局 layout id
        mAdapter.setEmptyViewLayout(this, R.layout.loading_view);

        mRecyclerView.setAdapter(mAdapter);
    }

    private int idAdd = 0;

    private void initClick() {
        itemChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DiffUtilDemoEntity> newData = getNewList();
                mAdapter.submitList(newData);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.add(2, new DiffUtilDemoEntity(
                        1111111 + idAdd,
                        "add - 😊😊Item " + 1111111 + idAdd,
                        "Item " + 0 + " content have change (notifyItemChanged)",
                        "06-12"));
                idAdd++;
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (2 >= mAdapter.getItems().size()) {
                    return;
                }
                mAdapter.removeAt(2);
            }
        });
    }


    /**
     * get new data
     *
     * @return
     */
    private List<DiffUtilDemoEntity> getNewList() {
        List<DiffUtilDemoEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            /*
            Simulate deletion of data No. 1 and No. 3
            模拟删除1号和3号数据
             */
            if (i == 1 || i == 3) {
                continue;
            }

            /*
            Simulate modification title of data No. 0
            模拟修改0号数据的title
             */
            if (i == 0) {
                list.add(new DiffUtilDemoEntity(
                        i,
                        "😊Item " + i,
                        "This item " + i + " content",
                        "06-12")
                );
                continue;
            }

            /*
            Simulate modification content of data No. 4
            模拟修改4号数据的content发生变化
             */
            if (i == 4) {
                list.add(new DiffUtilDemoEntity(
                        i,
                        "Item " + i,
                        "Oh~~~~~~, Item " + i + " content have change",
                        "06-12")
                );
                continue;
            }

            list.add(new DiffUtilDemoEntity(
                    i,
                    "Item " + i,
                    "This item " + i + " content",
                    "06-12")
            );
        }
        return list;
    }
}
