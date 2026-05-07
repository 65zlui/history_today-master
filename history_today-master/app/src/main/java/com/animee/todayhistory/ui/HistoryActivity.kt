package com.animee.todayhistory.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.animee.todayhistory.R
import com.animee.todayhistory.bean.HistoryBean

class HistoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var emptyTv: TextView
    private lateinit var historyLv: ListView
    private lateinit var backIv: ImageView
    private val mDatas = mutableListOf<HistoryBean.ResultBean>()
    private lateinit var adapter: HistoryAdapter
    private var historyBean: HistoryBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        emptyTv = findViewById(R.id.history_tv)
        historyLv = findViewById(R.id.history_lv)
        backIv = findViewById(R.id.history_iv_back)
        backIv.setOnClickListener(this)

        adapter = HistoryAdapter(this, mDatas)
        historyLv.adapter = adapter

        try {
            val bundle = intent.extras
            historyBean = bundle?.getSerializable("history") as? HistoryBean
            historyBean?.result?.let { list ->
                mDatas.addAll(list)
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            emptyTv.visibility = View.VISIBLE
        }

        historyLv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (position in mDatas.indices) {
                val intent = Intent(this, HistoryDescActivity::class.java)
                intent.putExtra("hisId", mDatas[position].e_id)
                startActivity(intent)
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.history_iv_back) {
            finish()
        }
    }
}
