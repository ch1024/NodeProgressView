package com.chen.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        RoundSeekBar roundSeekBar = findViewById(R.id.round_seek);
        WindowView window = findViewById(R.id.window_top);

//        roundSeekBar.getRoundSeekBar().setRoundSeekBarListener(new RoundSeekBar.RoundSeekBarListener() {
//            @Override
//            public void onStartTrackingTouch(RoundSeekBar seekBar) {
//                Log.e("11111", "开始: " );
//            }
//
//            @Override
//            public void onStopTrackingTouch(RoundSeekBar seekBar) {
//                Log.e("11111", "结束: " );
//            }
//
//            @Override
//            public void onProgressChanged(RoundSeekBar seekBar, int progress) {
//                Log.e("11111", "改变: "+progress );
//            }
//        });

        //转换为max（定义xml 1000范围内）

        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
        list.add(86);
        list.add(100);
        list.add(500);
        list.add(510);
        list.add(560);
        list.add(580);
        list.add(600);
        list.add(610);
        list.add(630);
        list.add(800);
//        list.add(1000);
        //设置参数
        RoundSeekData data = new RoundSeekData();
        data.setBackgroundColor(Color.GREEN);  //设置背景色
        data.setBackgroundRadius(true); //设置圆角
        data.setMaxProgress(1000);
        data.setNodeColor(Color.BLACK);
        data.setThumbColor(Color.BLUE);
        data.setLayout_width(LinearLayout.LayoutParams.MATCH_PARENT);
        data.setLayout_height(120);
        data.setCurrentProgress(100);
        window.setRoundSeekBarData(data);
        window.setWindowWidth(150,100);
        TextView textView = new TextView(this);
        textView.setText("aaaaa");
        window.showTopWindowView(textView);


        //
    }
}