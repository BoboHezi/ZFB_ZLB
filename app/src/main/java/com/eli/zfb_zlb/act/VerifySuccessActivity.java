package com.eli.zfb_zlb.act;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class VerifySuccessActivity extends BaseActivity {

    private Button backBtn;

    private int count = 3;

    private Timer countDownTimer = new Timer();

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                backBtn.setText("返回（" + (--count) + "）");
                if (count == 0) {
                    countDownTimer.cancel();
                    countDownTimer.purge();
                    finish();
                }
            });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setColor(this, 0xffb0b0b0);

        setContentView(R.layout.activity_verified);

        backBtn = findViewById(R.id.back_btn);

        setProgressListener(progress -> {
            if (progress == 100) {
                backBtn.setOnClickListener(this::back);
                if (countDownTimer != null) {
                    countDownTimer.schedule(task, 0, 1000);
                }
            }
        });
    }

    @Override
    public void finish() {
        countDownTimer.cancel();
        countDownTimer.purge();
        countDownTimer = null;
        setResult(103);
        super.finish();
    }
}
