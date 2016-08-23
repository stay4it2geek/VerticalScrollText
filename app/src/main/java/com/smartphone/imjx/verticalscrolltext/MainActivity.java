package com.smartphone.imjx.verticalscrolltext;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends Activity {

    private int num = 0;

    private String[] mStrData = {"点击查看选择的位置 ", "苏宁看不下去，全场免费送！", "58同城来凑热闹!", "京东大促,全场5元!", "天猫不服,全场不要钱!", "苏宁看不下去，全场免费送！"};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerticalScrollTextView layout = (VerticalScrollTextView) findViewById(R.id.rollview);

        RelativeLayout.LayoutParams tparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//定义显示组件参数

        for (int i = 0; i < mStrData.length; i++) {
            final View contentView = getLayoutInflater().inflate(R.layout.activity_content, null);
            TextView textView = (TextView) contentView.findViewById(R.id.textView);
            textView.setText(mStrData[i]);
            contentView.setTag(i);
            layout.addView(contentView, tparams);
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"点击了第"+contentView.getTag()+"个",Toast.LENGTH_SHORT).show();
                }
            });
        }


        layout.setOnPreTextChangeListener(new VerticalScrollTextView.OnPreTextChangeListener() {
            @Override
            public void SetOnPreTextChangeListener(RelativeLayout contentView) {
                if (num >= mStrData.length) {
                    num = 0;
                }
                ((TextView) contentView.findViewById(R.id.textView)).setText(mStrData[num]);
                num++;
            }
        });
    }


}
