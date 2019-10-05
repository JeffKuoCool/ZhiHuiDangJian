package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.lfc.zhihuidangjianapp.R;

import com.lfc.zhihuidangjianapp.base.BaseActivity;

import java.io.File;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description: 视频详情
 */
public class Act_vido_Experience extends BaseActivity {

    private String studyStrongBureauId;

    private TextView tvAppTitle;
    private VideoView video_view;

    private String appTitle = "林草大讲堂";
    private TextView downlo;
    private boolean isExcute = false;//是否在执行
    private boolean isDownLoad = true;//是否下载
    private long mTaskId;//下载任务id
    private DownloadManager downloadManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vido_experience;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
        downlo = findViewById(R.id.downlo);
        tvAppTitle = findViewById(R.id.app_title);
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("url");
        appTitle = getIntent().getStringExtra("appTitle");
        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "测试视频");
        jzVideoPlayerStandard.thumbImageView.setImageDrawable(getDrawable(R.mipmap.d_icon_add_img));
        String path = Environment.getExternalStoragePublicDirectory("/baoshenDocument/").getAbsolutePath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        } /*else if (file.exists() && file.isDirectory()) {
            List<String> nameList = getFileName(file.listFiles());
            if (nameList != null && nameList.size() != 0) {
                for (int i = 0; i < bean.size(); i++) {
                    for (int j = 0; j < nameList.size(); j++) {

                        if (bean.get(i).getDocument_name().equals(nameList.get(j))) {
                            bean.get(i).setIsDownLoad(true);
                        } else {
                            bean.get(i).setIsDownLoad(false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }*/
        downlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownLoad) {
                    String path = Environment.getExternalStoragePublicDirectory("/baoshenDocument/").getAbsolutePath();
                    File file = new File(path);
                    File[] files = file.listFiles();
                    for (File f : files) {
                        if (!f.isDirectory() && "HIS中间表表结构V1.7.xlsx".equals(f.getName())) {
                          /*  //查看文件
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //设置intent的Action属性
                            intent.setAction(Intent.ACTION_VIEW);
                            //获取文件file的MIME类型
                            String type = getMIMEType(f);
                            //设置intent的data和Type属性。
                            intent.setDataAndType(Uri.fromFile(f), type);
                            Log.i("chh", "file ==" + file);*/
                            //获取文件file的MIME类型
                            String type = getMIMEType(f);
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromFile(new File(path));
                            intent.setDataAndType(uri, type);
                            //跳转
                            try {
                                startActivity(intent);
                            } catch (Exception e) {

                                Toast.makeText(Act_vido_Experience.this, "找不到打开此文件的应用！", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }


                } else {
                    if (isExcute) {
                        return;
                    }
                    isExcute = true;
                    //下载文件
                    //创建下载任务,downloadUrl就是下载链接
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://baoshen.aohuanit.com:22222//upload/file/2018-01-11/HIS中间表表结构V1.7.xlsx"));
                    //指定下载路径和下载文件名
                    request.setDestinationInExternalPublicDir("/baoshenDocument/", "HIS中间表表结构V1.7.xlsx");
                    //获取下载管理器
                    downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    //将下载任务加入下载队列，否则不会进行下载
                    mTaskId = downloadManager.enqueue(request);

                    //注册广播接收者，监听下载状态
                    getActivity().registerReceiver(receiver,
                            new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                }

            }
        });

    }

    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };

    //检查下载状态
    private void checkDownloadStatus() {
        isExcute = false;
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i("chh", ">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.i("chh", ">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Log.i("chh", ">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT).show();


                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.i("chh", ">>>下载失败");
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".xls", "application/vnd.ms-excel"},
            {".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}
    };

}
