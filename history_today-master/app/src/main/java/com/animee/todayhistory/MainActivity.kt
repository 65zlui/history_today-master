package com.animee.todayhistory

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.animee.todayhistory.bean.HistoryBean
import com.animee.todayhistory.bean.LaoHuangliBean
import com.animee.todayhistory.bean.LaoHuangliHoursBean
import com.animee.todayhistory.ui.HistoryActivity
import com.animee.todayhistory.ui.HistoryAdapter
import com.animee.todayhistory.ui.HistoryDescActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, MainContract.MainView {

    private lateinit var mainLv: ListView
    private lateinit var imgBtn: ImageButton
    private val mDatas = mutableListOf<HistoryBean.ResultBean>()
    private lateinit var adapter: HistoryAdapter
    private var historyBean: HistoryBean? = null
    private var presenter: MainPresenter? = null

    // Header views
    private lateinit var yinliTv: TextView
    private lateinit var dayTv: TextView
    private lateinit var weekTv: TextView
    private lateinit var yangliTv: TextView
    private lateinit var baijiTv: TextView
    private lateinit var wuxingTv: TextView
    private lateinit var chongshaTv: TextView
    private lateinit var jishenTv: TextView
    private lateinit var xiongshenTv: TextView
    private lateinit var yiTv: TextView
    private lateinit var jiTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        addHeaderAndFooterView()

        mainLv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataPosition = position - mainLv.headerViewsCount
            if (dataPosition in mDatas.indices) {
                val intent = Intent(this, HistoryDescActivity::class.java)
                intent.putExtra("hisId", mDatas[dataPosition].e_id)
                startActivity(intent)
            }
        }

        presenter = MainPresenter(this)
        presenter?.getHuangLi()
        presenter?.getHuangLiHours()
        presenter?.getHistory()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    private fun initViews() {
        mainLv = findViewById(R.id.main_lv)
        imgBtn = findViewById(R.id.main_imgbtn)
        imgBtn.setOnClickListener(this)
        adapter = HistoryAdapter(this, mDatas)
        mainLv.adapter = adapter
    }

    private fun addHeaderAndFooterView() {
        val headerView = LayoutInflater.from(this).inflate(R.layout.main_headerview, null)
        initHeaderView(headerView)
        mainLv.addHeaderView(headerView)

        val footerView = LayoutInflater.from(this).inflate(R.layout.main_footer, null)
        footerView.tag = "footer"
        footerView.setOnClickListener(this)
        mainLv.addFooterView(footerView)
    }

    private fun initHeaderView(headerView: View) {
        yinliTv = headerView.findViewById(R.id.main_header_tv_nongli)
        dayTv = headerView.findViewById(R.id.main_header_tv_day)
        weekTv = headerView.findViewById(R.id.main_header_tv_week)
        yangliTv = headerView.findViewById(R.id.main_header_tv_yangli)
        baijiTv = headerView.findViewById(R.id.main_header_tv_baiji)
        wuxingTv = headerView.findViewById(R.id.main_header_tv_wuxing)
        chongshaTv = headerView.findViewById(R.id.main_header_tv_chongsha)
        jishenTv = headerView.findViewById(R.id.main_header_tv_jishen)
        xiongshenTv = headerView.findViewById(R.id.main_header_tv_xiongshen)
        yiTv = headerView.findViewById(R.id.main_header_tv_yi)
        jiTv = headerView.findViewById(R.id.main_header_tv_ji)
    }

    private fun getWeek(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day)
        val weeks = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val index = cal.get(Calendar.DAY_OF_WEEK) - 1
        return weeks[if (index < 0) 0 else index]
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_imgbtn -> popCalendarDialog()
            else -> {
                if (v?.tag == "footer") {
                    val intent = Intent(this, HistoryActivity::class.java)
                    historyBean?.let {
                        val bundle = Bundle().apply {
                            putSerializable("history", it)
                        }
                        intent.putExtras(bundle)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun popCalendarDialog() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                presenter?.UpdateTime(year, month, dayOfMonth)
                presenter?.getHistory()
                presenter?.getHuangLi()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    // ===== MainContract.MainView implementations =====

    override fun getHuangLiSuccess(bean: LaoHuangliBean) {
        val resultBean = bean.result ?: return
        val yangliArr = resultBean.yangli?.split("-") ?: return
        val week = getWeek(
            yangliArr[0].toIntOrNull() ?: return,
            yangliArr[1].toIntOrNull() ?: return,
            yangliArr[2].toIntOrNull() ?: return
        )
        yinliTv.text = "农历 ${resultBean.yinli} (阴历)"
        yangliTv.text = "公历 ${yangliArr[0]}年${yangliArr[1]}月${yangliArr[2]}日 $week(阳历)"
        dayTv.text = yangliArr[2]
        weekTv.text = week
        baijiTv.text = "彭祖百忌:${resultBean.baiji}"
        wuxingTv.text = "五行:${resultBean.wuxing}"
        chongshaTv.text = "冲煞:${resultBean.chongsha}"
        jishenTv.text = "吉神宜趋:${resultBean.jishen}"
        xiongshenTv.text = "凶神宜忌:${resultBean.xiongshen}"
        yiTv.text = "宜:${resultBean.yi}"
        jiTv.text = "忌:${resultBean.ji}"
    }

    override fun getHuangLiError(throwable: Throwable) {
        Log.e("MainActivity", "LaoHuangLi error", throwable)
    }

    override fun getHistorySuccess(bean: HistoryBean) {
        historyBean = bean
        mDatas.clear()
        bean.result?.let { list ->
            for (i in 0 until minOf(5, list.size)) {
                mDatas.add(list[i])
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun getHistoryError(throwable: Throwable) {
        Log.e("MainActivity", "TodayInHistory error", throwable)
    }

    override fun getHuangLiHoursSuccess(bean: LaoHuangliHoursBean) {
        Log.d("MainActivity", "时辰信息获取成功，共${bean.result?.size ?: 0}个时辰")
    }

    override fun getHuangLiHoursError(throwable: Throwable) {
        Log.e("MainActivity", "HuangLiHours error", throwable)
    }
}
