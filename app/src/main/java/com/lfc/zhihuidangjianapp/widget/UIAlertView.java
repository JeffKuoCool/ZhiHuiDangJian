package com.lfc.zhihuidangjianapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;


/**
 * 仿IOS的弹出框
 *
 * @来自网上
 */
public class UIAlertView extends Dialog {

    private Context context;
    private String title;
    private String message;
    private String buttonLeftText;
    private String buttonRightText;
    private ClickListenerInterface clickListenerInterface;
    private TextView tvLeft;
    private TextView tvRight;

    public UIAlertView(Context context, String title, String message,
                       String buttonLeftText, String buttonRightText) {
        super(context, R.style.UIAlertViewStyle);

        this.context = context;
        this.title = title;
        this.message = message;
        this.buttonLeftText = buttonLeftText;
        this.buttonRightText = buttonRightText;
    }

    public interface ClickListenerInterface {
        public void doLeft();

        public void doRight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        inite();
    }

    public void inite() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ui_alert_view, null);
        setContentView(view);

        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        tvLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
        tvRight = (TextView) view.findViewById(R.id.tvBtnRight);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        if ("".equals(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        tvMessage.setText(message);
        tvLeft.setText(buttonLeftText);
        tvRight.setText(buttonRightText);
        if("".equals(buttonLeftText)){
            tvLeft.setVisibility(View.GONE);
        }
        tvLeft.setOnClickListener(new clickListener());
        tvRight.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);

    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int id = v.getId();
            switch (id) {
                case R.id.tvBtnLeft:
                    clickListenerInterface.doLeft();
                  /*  tvLeft.setTextColor(Color.parseColor("#77d4d6"));
                    tvRight.setTextColor(Color.parseColor("#a0a0a0"));*/

                    break;
                case R.id.tvBtnRight:
                    clickListenerInterface.doRight();
                    /*tvRight.setTextColor(Color.parseColor("#77d4d6"));
                    tvLeft.setTextColor(Color.parseColor("#a0a0a0"));*/
                    break;

                default:
                    break;
            }
        }
    }

    ;
}
