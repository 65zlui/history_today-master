package com.animee.todayhistory.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.animee.todayhistory.R;
import com.animee.todayhistory.bean.HistoryBean;

import java.util.List;

public class HistoryAdapter extends BaseAdapter{
    Context context;
    List<HistoryBean.ResultBean> mDatas;

    public HistoryAdapter(Context context, List<HistoryBean.ResultBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_timeline,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        HistoryBean.ResultBean resultBean = mDatas.get(position);
        
        // 始终显示时间布局
        holder.timeLayout.setVisibility(View.VISIBLE);
        
        // 使用date字段显示完整日期
        holder.timeTv.setText(resultBean.getDate());
        holder.titleTv.setText(resultBean.getTitle());
        
        return convertView;
    }

    class ViewHolder{
        TextView timeTv,titleTv;
        LinearLayout timeLayout;
        public ViewHolder(View itemView){
            timeTv = itemView.findViewById(R.id.item_main_time);
            titleTv = itemView.findViewById(R.id.item_main_title);
            timeLayout = itemView.findViewById(R.id.item_main_ll);
        }
    }

}
