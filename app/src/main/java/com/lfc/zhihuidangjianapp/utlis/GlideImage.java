package com.lfc.zhihuidangjianapp.utlis;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lfc.zhihuidangjianapp.R;

import com.youth.banner.loader.ImageLoader;


public class GlideImage extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        //eg：

        //Glide 加载图片简单用法
        try {
            String goods_thumb = (String) path;
            Glide.with(context)
                    .load(path)
                    // 自己实现BitmapTransformation
                    //                    .placeholder(loadImg) //设置加载中的图片
                    .into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Glide::", "===IllegalArgumentException: You cannot start a load for a destroyed activity");
        }

    }



}
