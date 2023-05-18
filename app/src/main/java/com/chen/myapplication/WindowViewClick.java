package com.chen.myapplication;

public interface WindowViewClick {
    void windowOnStartTrackingTouch(RoundSeekBar seekBar);
    void windowOnStopTrackingTouch(RoundSeekBar seekBar);
    void windowOnProgressChanged(RoundSeekBar seekBar, int progress);
}
