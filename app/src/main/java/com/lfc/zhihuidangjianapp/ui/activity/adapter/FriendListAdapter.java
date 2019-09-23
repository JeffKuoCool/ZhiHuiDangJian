package com.lfc.zhihuidangjianapp.ui.activity.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.ui.activity.model.Friend;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2019-09-05
 * @autror: guojian
 * @description: 通讯录
 */
public class FriendListAdapter extends CommonAdapter<User> {

    private List<Friend> selectUsers = new ArrayList<>();

    private int enter;

    private int lastPosition = -1;
    private ImageView lastImage;

    public FriendListAdapter(Context context, int layoutId, List<User> datas) {
        super(context, layoutId, datas);
        selectUsers.clear();
        for (User user : datas) {
            selectUsers.add(new Friend(false, user));
        }
    }

    public ArrayList<User> getSelectUser(){
        ArrayList<User> temp = new ArrayList<>();
        for (Friend friend: selectUsers){
            if(friend.isSelect()){
                temp.add(friend.getUser());
            }
        }
        return temp;
    }

    public void setEnter(int enter) {
        this.enter = enter;
    }

    @Override
    protected void convert(ViewHolder holder, User data, int position) {
        ImageView ivCheck = holder.getConvertView().findViewById(R.id.ivCheck);
        if (enter == 0) {
            holder.setVisible(R.id.ivCheck, false);
        } else {
            holder.setVisible(R.id.ivCheck, true);
        }
        holder.setText(R.id.tv_name, data.getSealName());
        ImageView image = holder.getConvertView().findViewById(R.id.iv_head);
        Glide.with(holder.getConvertView().getContext()).load(ApiConstant.ROOT_URL + data.getImgAddress()).into(image);
        holder.setText(R.id.tv_content, data.getSubordinatePartyGroup());
        holder.setText(R.id.tv_tell, data.getMobileNumber());
        holder.getConvertView().setOnClickListener(item -> {
            if (enter == 0) {//关闭

            } else if (enter == 1) {//单选
                ivCheck.setSelected(!ivCheck.isSelected());
                selectUsers.get(position).setSelect(ivCheck.isSelected());
                if (lastPosition >= 0) {
                    selectUsers.get(lastPosition).setSelect(false);
                    if(lastImage!=null){
                        lastImage.setSelected(false);
                    }
                }
                lastPosition = position;
                lastImage = ivCheck;
            } else if (enter == 2) {//多选
                ivCheck.setSelected(!ivCheck.isSelected());
                selectUsers.get(position).setSelect(ivCheck.isSelected());
            }
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(holder.getConvertView(), holder, position);
            }
        });
    }
}
