package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.bean.QueryHomeNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.Fgt_Home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseAdapter {

    private List<QueryHomeNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean> list;
    private Context context;

    public HomeAdapter(List<QueryHomeNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home, null);

            viewHolder.textView = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        QueryHomeNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean datasBean = list.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        viewHolder.textView.setText(datasBean.getAnnouncementTitle());
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}