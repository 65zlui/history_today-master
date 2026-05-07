package com.animee.todayhistory.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.animee.todayhistory.R
import com.animee.todayhistory.bean.HistoryDescBean
import com.squareup.picasso.Picasso

class HistoryDescActivity : AppCompatActivity(), View.OnClickListener, HistoryDescContact.View {

    private val TAG = "HistoryDescActivity"

    private lateinit var backIv: ImageView
    private lateinit var shareIv: ImageView
    private lateinit var picIv: ImageView
    private lateinit var titleTv: TextView
    private lateinit var contentTv: TextView
    private var resultBean: HistoryDescBean.ResultBean? = null
    private var presenter: HistoryDescPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_desc)

        backIv = findViewById(R.id.desc_back_iv)
        shareIv = findViewById(R.id.desc_share_iv)
        picIv = findViewById(R.id.desc_iv_pic)
        titleTv = findViewById(R.id.desc_tv_title)
        contentTv = findViewById(R.id.desc_tv_content)

        backIv.setOnClickListener(this)
        shareIv.setOnClickListener(this)

        val id = intent.getStringExtra("hisId")
        presenter = HistoryDescPresenter(this)
        id?.let { presenter?.loadGet(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.desc_back_iv -> finish()
            R.id.desc_share_iv -> shareContent()
        }
    }

    private fun shareContent() {
        val text = if (resultBean != null) {
            "想要了解${resultBean!!.title}详情么？快来下载历史上的今天App吧！"
        } else {
            "我发现一款好用的软件-历史上的今天，快来一起探索这个APP吧！"
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "历史上的今天"))
    }

    override fun showResult(bean: HistoryDescBean) {
        if (!bean.result.isNullOrEmpty()) {
            resultBean = bean.result[0]

            Log.d(TAG, resultBean.toString())

            titleTv.text = resultBean!!.title
            contentTv.text = resultBean!!.content

            val picUrl = resultBean!!.picUrl
            if (!picUrl.isNullOrEmpty()) {
                val firstUrl = picUrl[0].url
                if (!TextUtils.isEmpty(firstUrl)) {
                    picIv.visibility = View.VISIBLE
                    Picasso.get().load(firstUrl).into(picIv)
                } else {
                    picIv.visibility = View.GONE
                }
            } else {
                picIv.visibility = View.GONE
            }
        } else {
            titleTv.text = "暂无数据"
            contentTv.text = "未找到相关历史事件详情"
            picIv.visibility = View.GONE
        }
    }
}
