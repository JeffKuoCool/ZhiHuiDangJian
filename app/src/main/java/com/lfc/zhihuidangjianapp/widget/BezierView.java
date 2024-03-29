package com.lfc.zhihuidangjianapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lfc.zhihuidangjianapp.ui.activity.model.DeptDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @date: 2019-09-12
 * @autror: guojian
 * @description: 贝塞尔曲线图
 */
public class BezierView extends View {

    private List<DeptDetail.OLisfForEacherList> mData = new ArrayList<>();

    private Context mContext;

    private float mWidth, mHeight;

    //x轴
    private int countX = 11;
    //y轴
    private int countY = 5;

    private Paint mCirclePaint, mCubicPaint, mtextPaint, mXPaint;

    private float radio = 4;

    private float margin = 12;

    public BezierView(Context context) {
        super(context);
        init(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData.isEmpty()) {
            return;
        }
        //文本画笔
        mtextPaint = new Paint();
        mtextPaint.setAntiAlias(true);
        mtextPaint.setTextSize(24);
        mtextPaint.setTextAlign(Paint.Align.CENTER);
        //水平虚线画笔
        mXPaint = new Paint();
        mXPaint.setAntiAlias(true);
        mXPaint.setStyle(Paint.Style.STROKE);
        mXPaint.setPathEffect(new DashPathEffect(new float[]{3, 2}, 0));
        //圆点画笔
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        //曲线画笔
        mCubicPaint = new Paint();
        mCubicPaint.setAntiAlias(true);
        mCubicPaint.setStyle(Paint.Style.STROKE);
        mCubicPaint.setStrokeWidth(3);
        //坐标轴空出文本宽度
        float textWidth = mtextPaint.getTextSize() + margin;

        mWidth = getWidth() - 3 * textWidth;
        mHeight = getHeight() - 2 * textWidth;

        int maxData = Collections.max(partIntegerList(mData));
        int minData = Collections.min(partIntegerList(mData));
        //数据源y轴数据都是0赋值默认最大值
        if (maxData == 0) {
            maxData = 10;
        }

        float base = mHeight / (maxData - minData);

        int baseHeightY = (maxData - minData) / countY;
        //原点文字
        canvas.drawText("0", textWidth, mHeight + textWidth + textWidth / 4, mtextPaint);
        //x轴
        canvas.drawLine(2 * textWidth, mHeight + textWidth, mWidth + 2 * textWidth + margin, mHeight + textWidth, mXPaint);
        for (int i = 0; i < countY; i++) {
            //画水平线
            canvas.drawLine(2 * textWidth, i * mHeight/countY + textWidth, mWidth + 2 * textWidth + margin, i * mHeight/countY + textWidth, mXPaint);
            //y轴文字
            canvas.drawText(maxData - i * baseHeightY+"", textWidth, i * mHeight/countY + textWidth + textWidth / 4, mtextPaint);
        }

        for (int i = 0; i < mData.size(); i++) {
            DeptDetail.OLisfForEacherList detail = mData.get(i);
            //点圆心
            float centerX = mWidth / countX * (detail.getMonth() - 1) + radio + 2 * textWidth;
            float centerY = mHeight - base * (detail.getArticalCount() - minData) + textWidth;

            //绘制x轴数据
            canvas.drawText(detail.getMonth() + "月", centerX, mHeight + 2 * textWidth - margin, mtextPaint);
            //绘制点
            canvas.drawCircle(centerX, centerY, radio, mCirclePaint);
            //绘制数据曲线
            if (i > 0) {
                DeptDetail.OLisfForEacherList lastDetail = mData.get(i - 1);
                float lastCenterX = mWidth / countX * (lastDetail.getMonth() - 1) + radio + 2 * textWidth;
                float lastCenterY = mHeight - base * (lastDetail.getArticalCount() - minData) + textWidth;

                Path path = new Path();
                path.moveTo(lastCenterX, lastCenterY);
                path.cubicTo(lastCenterX + mWidth / countX / 2, lastCenterY, lastCenterX + mWidth / countX / 2, centerY,
                        centerX, centerY);
                canvas.drawPath(path, mCubicPaint);
            }
        }

    }

    private List<Integer> partIntegerList(List<DeptDetail.OLisfForEacherList> list) {
        List<Integer> newlist = new ArrayList<>();
        for (DeptDetail.OLisfForEacherList detail : list) {
            newlist.add(detail.getArticalCount());
        }
        return newlist;
    }

    public void setData(List<DeptDetail.OLisfForEacherList> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        if (data.size() < countX) {
            //月数不足12根据已有数据重组
            List<DeptDetail.OLisfForEacherList> newlist = new ArrayList<>();
            for (int i = 0; i < countX; i++) {
                DeptDetail.OLisfForEacherList newDetail = new DeptDetail.OLisfForEacherList(0, i + 1);
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).getMonth() == (i + 1)) {
                        newDetail = data.get(j);
                    }
                }
                newlist.add(newDetail);
            }
            mData = newlist;
        } else {
            mData = data;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

}
