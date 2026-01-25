package com.animee.todayhistory.ui;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.todayhistory.R;
import com.animee.todayhistory.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView emptyTv;
    private ListView historyLv;
    private ImageView backIv;
    List<HistoryBean.ResultBean> mDatas;
    private HistoryAdapter adapter;
    HistoryBean historyBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        emptyTv = (TextView) findViewById(R.id.history_tv);
        historyLv = (ListView) findViewById(R.id.history_lv);
        backIv = (ImageView) findViewById(R.id.history_iv_back);
        backIv.setOnClickListener(this);
        mDatas = new ArrayList<>();
        adapter = new HistoryAdapter(this, mDatas);
        historyLv.setAdapter(adapter);

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            historyBean = (HistoryBean)bundle.getSerializable("history");
            List<HistoryBean.ResultBean> list = historyBean.getResult();
            mDatas.addAll(list);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            emptyTv.setVisibility(View.VISIBLE);
        }

        historyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 确保点击的是数据项，避免数组越界
                if (position >= 0 && position < mDatas.size()) {
                    Intent intent = new Intent(HistoryActivity.this, HistoryDescActivity.class);
                    HistoryBean.ResultBean resultBean = mDatas.get(position);
                    String bean_id = resultBean.getE_id();
                    intent.putExtra("hisId", bean_id);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.history_iv_back) {
            finish();
        }
    }
}
