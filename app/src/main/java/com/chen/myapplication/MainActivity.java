package com.chen.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RoundSeekBar roundSeekBar = findViewById(R.id.round_seek);
        roundSeekBar.setRoundSeekBarListener(new RoundSeekBar.RoundSeekBarListener() {
            @Override
            public void onStartTrackingTouch(RoundSeekBar seekBar) {
                Log.e("11111", "开始: " );
            }

            @Override
            public void onStopTrackingTouch(RoundSeekBar seekBar) {
                Log.e("11111", "结束: " );
            }

            @Override
            public void onProgressChanged(RoundSeekBar seekBar, int progress) {
                Log.e("11111", "改变: "+progress );
            }
        });

        //转换为max（定义xml 1000范围内）

        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
        list.add(86);
//        list.add(100);
//        list.add(500);
//        list.add(400);
//        list.add(800);
//        list.add(1000);
        roundSeekBar.setNode(list);


        findViewById(R.id.seek_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundSeekBar.setProgress(roundSeekBar.getProgress()+1,true);
            }
        });
    }
}