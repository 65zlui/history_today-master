package com.animee.todayhistory.ui;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.animee.todayhistory.R;
import com.animee.todayhistory.ui.base.BaseActivity;
import com.animee.todayhistory.ui.base.ContentURL;
import com.animee.todayhistory.bean.HistoryDescBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class HistoryDescActivity extends AppCompatActivity implements View.OnClickListener, HistoryDescContact.View{
    private String TAG = "HistoryDescActivity";
    private ImageView backIv,shareIv,picIv;
    private TextView titleTv;
    private TextView contentTv;
    private String hisId;

    HistoryDescBean.ResultBean resultBean;
    HistoryDescPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_desc);
        backIv    = findViewById(R.id.desc_back_iv);
        shareIv   = findViewById(R.id.desc_share_iv);
        picIv     = findViewById(R.id.desc_iv_pic);
        titleTv   = findViewById(R.id.desc_tv_title);
        contentTv = findViewById(R.id.desc_tv_content);
        backIv.setOnClickListener(this);
        shareIv.setOnClickListener(this);

        hisId = getIntent().getStringExtra("hisId");
        presenter = new HistoryDescPresenter(this);
        presenter.loadGet(hisId);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.desc_back_iv:
                finish();
                break;
            case R.id.desc_share_iv:
                String text = "我发现一款好用的软件-历史上的今天，快来一起探索这个APP吧！";
                if (resultBean!=null) {
                    text = "想要了解"+resultBean.getTitle()+"详情么？快来下载历史上的今天App吧！";
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(intent,"历史上的今天"));
                break;
        }
    }



    @Override
    public void showResult(HistoryDescBean bean) {
        resultBean = bean.getResult().get(0);

        final HistoryDescActivity historyDescActivity = this;

        Log.d(TAG, String.valueOf(resultBean));
        String picUrl = resultBean.getPic();
        titleTv.setText(resultBean.getTitle());
        contentTv.setText(resultBean.getContent());
        if (TextUtils.isEmpty(picUrl)) {
            picIv.setVisibility(View.GONE);
        }else {
            picIv.setVisibility(View.VISIBLE);
            Picasso.with(historyDescActivity).load(picUrl).into(picIv);
        }


    }




}
