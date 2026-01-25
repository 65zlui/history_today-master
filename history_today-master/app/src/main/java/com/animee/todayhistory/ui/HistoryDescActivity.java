package com.animee.todayhistory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.animee.todayhistory.R;
import com.animee.todayhistory.bean.HistoryDescBean;
import com.squareup.picasso.Picasso;

public class HistoryDescActivity extends androidx.appcompat.app.AppCompatActivity implements View.OnClickListener, HistoryDescContact.View{
    private String TAG = "HistoryDescActivity";
    private ImageView backIv,shareIv,picIv;
    private TextView titleTv;
    private TextView contentTv;
    private String id;

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

        id = getIntent().getStringExtra("id");
        presenter = new HistoryDescPresenter(this);
        presenter.loadGet(id);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.desc_back_iv) {
            finish();
        } else if (v.getId() == R.id.desc_back_iv) {
            String text = "我发现一款好用的软件-历史上的今天，快来一起探索这个APP吧！";
            if (resultBean != null) {
                text = "想要了解" + resultBean.getTitle() + "详情么？快来下载历史上的今天App吧！";
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(intent, "历史上的今天"));
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
            Picasso.get().load(picUrl).into(picIv);
        }


    }




}
