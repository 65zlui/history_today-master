package com.animee.todayhistory.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.animee.todayhistory.R
import com.animee.todayhistory.bean.HistoryBean


class HistoryAdapter(var context: Context?, var mDatas: MutableList<HistoryBean.ResultBean>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any? {
        return mDatas.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_timeline, null)
            holder = ViewHolder(convertView!!)
            convertView.setTag(holder)
        } else {
            holder = convertView.getTag() as ViewHolder?
        }
        val resultBean = mDatas.get(position)


        // 始终显示时间布局
        holder!!.timeLayout.setVisibility(View.VISIBLE)


        // 使用date字段显示完整日期
        holder.timeTv.setText(resultBean.date)
        holder.titleTv.setText(resultBean.title)

        return convertView
    }

    internal inner class ViewHolder(itemView: View) {
        var timeTv: TextView
        var titleTv: TextView
        var timeLayout: LinearLayout

        init {
            timeTv = itemView.findViewById<TextView?>(R.id.item_main_time)
            titleTv = itemView.findViewById<TextView?>(R.id.item_main_title)
            timeLayout = itemView.findViewById<LinearLayout?>(R.id.item_main_ll)
        }
    }
}
