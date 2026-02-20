package com.animee.todayhistory;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.todayhistory.ui.HistoryActivity;
import com.animee.todayhistory.ui.HistoryAdapter;
import com.animee.todayhistory.ui.HistoryDescActivity;
import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
import com.animee.todayhistory.bean.LaoHuangliHoursBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainContract.MainView{

    private ListView mainLv;
    private ImageButton imgBtn;
    List<HistoryBean.ResultBean> mDatas;
    private HistoryAdapter adapter;

    HistoryBean historyBean;
    private MainPresenter presenter;
//    声明头布局当中的TextView
    TextView yinliTv,dayTv,weekTv,yangliTv,baijiTv,wuxingTv,chongshaTv,jishenTv,xiongshenTv,yiTv,jiTv;

    private Calendar calendar;
    private Date date;


    LaoHuangliBean huangliBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
//        获取日历对象
        calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        addHeaderAndFooterView();
//        String todayHistoryURL = ContentURL.getTodayHistoryURL("1.0", month, day);
//        loadData(todayHistoryURL);
        /* 因为ListView添加头布局了，所以position对应集合的位置发生变化，集合第0个数据，position为第1个数据，所以要减掉一个*/
        mainLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 确保点击的是数据项，而不是headerView或footerView
                int dataPosition = position - mainLv.getHeaderViewsCount();
                if (dataPosition >= 0 && dataPosition < mDatas.size()) {
                    Intent intent = new Intent(MainActivity.this, HistoryDescActivity.class);
                    HistoryBean.ResultBean resultBean = mDatas.get(dataPosition);
                    String bean_id = resultBean.getE_id();
                    intent.putExtra("hisId", bean_id);
                    startActivity(intent);
                }
            }
        });

        presenter = new MainPresenter(this);
        presenter.getHuangLi();
        presenter.getHuangLiHours();
        presenter.getHistory();

    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    public void initViews() {
        mainLv = (ListView) findViewById(R.id.main_lv);
        imgBtn = (ImageButton) findViewById(R.id.main_imgbtn);
        imgBtn.setOnClickListener(this);

        mDatas = new ArrayList<>();
        adapter = new HistoryAdapter(this, mDatas);
        mainLv.setAdapter(adapter);
    }

    private void addHeaderAndFooterView() {
//        给ListView添加头尾布局函数
        View headerView = LayoutInflater.from(this).inflate(R.layout.main_headerview,null);
        initHeaderView(headerView);
        mainLv.addHeaderView(headerView);
        View footerView = LayoutInflater.from(this).inflate(R.layout.main_footer,null);
        footerView.setTag("footer");
        footerView.setOnClickListener(this);
        mainLv.addFooterView(footerView);
    }

    private void initHeaderView(View headerView) {
        /* 初始化ListView头布局当中的每一个控件*/
        yinliTv = headerView.findViewById(R.id.main_header_tv_nongli);
        dayTv = headerView.findViewById(R.id.main_header_tv_day);
        weekTv = headerView.findViewById(R.id.main_header_tv_week);
        yangliTv = headerView.findViewById(R.id.main_header_tv_yangli);
        baijiTv = headerView.findViewById(R.id.main_header_tv_baiji);
        wuxingTv = headerView.findViewById(R.id.main_header_tv_wuxing);
        chongshaTv = headerView.findViewById(R.id.main_header_tv_chongsha);
        jishenTv = headerView.findViewById(R.id.main_header_tv_jishen);
        xiongshenTv = headerView.findViewById(R.id.main_header_tv_xiongshen);
        yiTv = headerView.findViewById(R.id.main_header_tv_yi);
        jiTv = headerView.findViewById(R.id.main_header_tv_ji);
//        将日期对象转换成指定格式的字符串形式
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String time = sdf.format(date);
//        String laohuangliURL = ContentURL.getLaohuangliURL(time);
        //laohuangliURL

    }



    private String getWeek(int year, int month, int day) {
//        根据年月日获取对应的星期
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        int index = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (index<0){
            index = 0;
        }
        return weeks[index];
    }


    @Override
    public void onClick(View v) {
        if (v.getId() ==  R.id.main_imgbtn) {
            popCalendarDialog();
            return;
        }

        String tag = (String) v.getTag();
        if (tag.equals("footer")) {
            Intent intent = new Intent(this, HistoryActivity.class);
            if (historyBean!=null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("history",historyBean);
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    private void popCalendarDialog() {
//        弹出日历对话框
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                改变老黄历显示的内容
                String time = year+"-"+(month+1)+"-"+dayOfMonth;
                presenter.UpdateTime(year,month,dayOfMonth);
                presenter.getHistory();
                presenter.getHuangLi();
//                presenter.getHuangLi();
//                presenter.getHistory();
//                String laohuangliURL = ContentURL.getLaohuangliURL(time);
//                loadHeaderData(laohuangliURL);


//                改变历史上的今天数据
//                String todayHistoryURL = ContentURL.getTodayHistoryURL("1.0", (month + 1), dayOfMonth);
//                presenter.getHistory();
//                loadData(todayHistoryURL);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    @Override
    public void getHuangLiSuccess(final LaoHuangliBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final LaoHuangliBean.ResultBean resultBean = bean.getResult();
                final String[] yangliArr = resultBean.getYangli().split("-");
                final String week = getWeek(Integer.parseInt(yangliArr[0]), Integer.parseInt(yangliArr[1]), Integer.parseInt(yangliArr[2]));
                yinliTv.setText("农历 "+resultBean.getYinli()+" (阴历)");
                yangliTv.setText("公历 "+yangliArr[0]+"年"+yangliArr[1]+"月"+yangliArr[2]+"日 "+week+"(阳历)");
                dayTv.setText(yangliArr[2]);
                weekTv.setText(week);
                baijiTv.setText("彭祖百忌:"+resultBean.getBaiji());
                wuxingTv.setText("五行:"+resultBean.getWuxing());
                chongshaTv.setText("冲煞:"+resultBean.getChongsha());
                jishenTv.setText("吉神宜趋:"+resultBean.getJishen());
                xiongshenTv.setText("凶神宜忌:"+resultBean.getXiongshen());
                yiTv.setText("宜:"+resultBean.getYi());
                jiTv.setText("忌:"+resultBean.getJi());
            }
        });
    }

    @Override
    public void getHuangLiError(final Throwable throwable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void getHistorySuccess(final HistoryBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                historyBean = bean;
                mDatas.clear();
                List<HistoryBean.ResultBean> list = historyBean.getResult();
                for (int i = 0; i < 5 && i < list.size(); i++) {
                    mDatas.add(list.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getHistoryError(final Throwable throwable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("Exception","HistoryInToday",throwable);
            }
        });
    }

    @Override
    public void getHuangLiHoursSuccess(final LaoHuangliHoursBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 时辰信息获取成功，这里可以根据需要处理时辰数据
                // 目前UI上没有显示时辰信息的控件，所以暂时只做日志输出
                Log.d("MainActivity", "时辰信息获取成功，共" + bean.getResult().size() + "个时辰");
            }
        });
    }

    @Override
    public void getHuangLiHoursError(final Throwable throwable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("Exception", "HuangLiHoursError", throwable);
            }
        });
    }
}
